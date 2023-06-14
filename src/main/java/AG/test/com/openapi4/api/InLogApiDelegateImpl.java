package AG.test.com.openapi4.api;

import AG.test.com.openapi4.exception.ValidationException;
import AG.test.com.openapi4.services.DatabaseAuthenticationService;
import AG.test.com.openapi4.services.KeyCloakService;
import AG.test.com.openapi4.validator.ValidateBasicAuthorization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class InLogApiDelegateImpl implements InlogApiDelegate
{

    @Value("${header.Authorization}")
    private String Authorization;
    @Value("${header.Message}")
    private String Message;
    @Value("${header.Message.Success}")
    private String success;
    @Value("${header.Message.InteralError}")
    private String internalError;

    @Override
    public ResponseEntity<Void> getJTW(String authorization)
    {
        HttpHeaders header = new HttpHeaders();

        //validation on input authorization value
        try
        {
            ValidateBasicAuthorization.validate(authorization);
        }
        catch (ValidationException e)
        {
            header.add(Authorization, "");
            header.add(Message, e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(header).build();
        }

        //check credential in database
        try
        {
            DatabaseAuthenticationService.checkDatabaseAuth(authorization);
        }
        catch (ValidationException e)
        {
            header.add(Authorization, "");
            header.add(Message, e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(header).build();
        }


        //get JWT from KezCloak
        try
        {
            String JTW = KeyCloakService.getJWT(authorization);

            header.add(Authorization, JTW);
            header.add(Message, success);

            return ResponseEntity.ok().headers(header).build();
        }
        catch (ValidationException e)
        {
            header.add(Authorization, "");
            header.add(Message, e.getMessage());
        }

        header.add(Authorization, "");
        header.add(Message, internalError);
        return ResponseEntity.ok().headers(header).build();
    }


}
