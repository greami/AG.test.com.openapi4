package AG.test.com.openapi4.exception;

import org.springframework.beans.factory.annotation.Value;

public class PublicKeyException extends ValidationException
{
    @Value( "${BasicAuthorizationWrongInputException.msg}" )
    private String msg = "Can not retrieve public key";


    public  PublicKeyException()
    {
        super.setMsg(msg);
    }

}
