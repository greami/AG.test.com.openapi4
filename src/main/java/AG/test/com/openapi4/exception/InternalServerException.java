package AG.test.com.openapi4.exception;

import org.springframework.beans.factory.annotation.Value;

public class InternalServerException extends ValidationException
{
    @Value( "${InternalServerException.msg}" )
    private String msg;

    public InternalServerException()
    {
        super.setMsg(msg);
    }

}
