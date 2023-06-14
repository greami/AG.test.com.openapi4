package AG.test.com.openapi4.api;

import AG.test.com.openapi4.exception.JWTnotValidException;
import AG.test.com.openapi4.exception.ValidationException;
import AG.test.com.openapi4.model.UserDetail;
import AG.test.com.openapi4.services.KeyCloakService;
import AG.test.com.openapi4.validator.ValidateBearerAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MyresApiDelegateImpl implements MyresApiDelegate
{

    @Value("${header.Authorization}")
    private String Authorization;
    @Value("${header.Message}")
    private String Message;

    @Override
    public ResponseEntity<UserDetail> getResource(String authorization)
    {

        String publicKey;

        //validate input, validate Bear authorization
        HttpHeaders header = new HttpHeaders();

        try
        {
            ValidateBearerAuthentication.validate(authorization);
        }
        catch (ValidationException e)
        {
            header.add(Authorization, "");
            header.add(Message, e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(header).build();
        }

        //get public key
        try
        {
            publicKey = KeyCloakService.getPublicKey();
        }
        catch (ValidationException e)
        {
            header.add(Authorization, "");
            header.add(Message, e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(header).build();
        }

/*
        //get public key
        try
        {
            publicKey = KeyCloakService.getPublicKey();
        }
        catch (ValidationException e)
        {
//            header.add(Authorization, "");
            header.add(Message, e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(header).build();
        }
*/

        //validate JTW with publicKez
        try
        {
            String JWT = authorization.replace("Bearer", " ").trim();
            KeyCloakService.validateJWTwithPublicKey(publicKey, JWT);
        }
        catch (JWTnotValidException e)
        {
//            header.add(Authorization, "");
            header.add(Message, e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(header).build();
        }
        catch (ValidationException e)
        {
//            header.add(Authorization, "");
            header.add(Message, e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(header).build();
        }


        //return resource MOCK
        UserDetail userDetail = new UserDetail();
        userDetail.setId(111);
        userDetail.setName("mario");
        userDetail.setSurname("rossi");
        userDetail.setUsername("Marros");

        return ResponseEntity.ok(userDetail);
    }
}
