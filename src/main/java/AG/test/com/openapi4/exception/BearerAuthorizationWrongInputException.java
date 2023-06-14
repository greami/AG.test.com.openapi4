package AG.test.com.openapi4.exception;

import org.springframework.beans.factory.annotation.Value;

public class BearerAuthorizationWrongInputException extends ValidationException
{

    @Value( "${BearerAuthorizationWrongInputException.msg}" )
    private String msg;

    public BearerAuthorizationWrongInputException()
    {
        super.setMsg(msg);
    }
}
