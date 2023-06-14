package AG.test.com.openapi4.validator;

import AG.test.com.openapi4.exception.BasicAuthorizationWrongInputException;
import AG.test.com.openapi4.exception.ValidationException;

import java.util.Base64;

public class ValidateBasicAuthorization implements Validator
{

    public static void validate(String authorization) throws ValidationException
    {
        //check on Basic Auth value
        if(authorization==null)
        {
            throw new BasicAuthorizationWrongInputException();
        }

        if(authorization.isBlank()  || authorization.isEmpty() )
        {
            throw new BasicAuthorizationWrongInputException();
        }

        if(!authorization.contains("Basic"))
        {
            throw new BasicAuthorizationWrongInputException();
        }

        String usernameAndPassword64 = authorization.replace("Basic", " ").trim();

        if(usernameAndPassword64==null)
        {
            throw new BasicAuthorizationWrongInputException();
        }

        if(usernameAndPassword64.isBlank()  || usernameAndPassword64.isEmpty() )
        {
            throw new BasicAuthorizationWrongInputException();
        }

        byte[] usernameAndPasswordByte =  Base64.getDecoder().decode(usernameAndPassword64);

        if(usernameAndPasswordByte==null)
        {
            throw new BasicAuthorizationWrongInputException();
        }

        if(usernameAndPasswordByte.length<=0)
        {
            throw new BasicAuthorizationWrongInputException();
        }

        String usernameAndPassword = new String(usernameAndPasswordByte);

        if(!usernameAndPassword.contains(":"))
        {
            throw new BasicAuthorizationWrongInputException();
        }

        //check on username and password
        String[] usernameAndPasswordArray = usernameAndPassword.split(":");
        if(usernameAndPasswordArray== null)  { throw new BasicAuthorizationWrongInputException();  }
        if(usernameAndPasswordArray.length!=2) {throw new BasicAuthorizationWrongInputException();  }
    }




}
