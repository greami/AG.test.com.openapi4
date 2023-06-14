package AG.test.com.openapi4.exception;

import org.springframework.beans.factory.annotation.Value;

public class BasicAuthorizationWrongInputException extends ValidationException
{
    @Value( "${BasicAuthorizationWrongInputException.msg}" )
    private String msg;

    public BasicAuthorizationWrongInputException()
    {
        super.setMsg(msg);
    }

}
