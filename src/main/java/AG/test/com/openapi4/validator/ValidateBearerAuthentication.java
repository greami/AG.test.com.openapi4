package AG.test.com.openapi4.validator;

import AG.test.com.openapi4.exception.BasicAuthorizationWrongInputException;
import AG.test.com.openapi4.exception.ValidationException;

public class ValidateBearerAuthentication implements Validator
{


    public static void validate(String authJWT) throws ValidationException
    {
        if(authJWT==null)
        {
            throw new BasicAuthorizationWrongInputException();
        }

        if(authJWT.isEmpty() || authJWT.isBlank())
        {
            throw new BasicAuthorizationWrongInputException();
        }

        if(!authJWT.contains("Bearer"))
        {
            throw new BasicAuthorizationWrongInputException();
        }

        String JWT = authJWT.replace("Bearer", " "). trim();

        if(JWT == null)
        {
            throw new BasicAuthorizationWrongInputException();
        }

        if(JWT.isBlank() || JWT.isEmpty())
        {
            throw new BasicAuthorizationWrongInputException();
        }

    }
}
