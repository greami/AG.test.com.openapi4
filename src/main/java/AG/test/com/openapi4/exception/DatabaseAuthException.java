package AG.test.com.openapi4.exception;

import org.springframework.beans.factory.annotation.Value;

public class DatabaseAuthException extends ValidationException
{
    @Value( "${BasicAuthorizationWrongInputException.msg}" )
    private String msg;

    public DatabaseAuthException()
    {
        super.setMsg(msg);
    }
}
