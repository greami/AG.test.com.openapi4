package AG.test.com.openapi4.exception;

import org.springframework.beans.factory.annotation.Value;

public class JWTnotValidException extends ValidationException
{
    @Value("${JWTnotValidException.msg}")
    private String msg;

    public JWTnotValidException()
    {
        super.setMsg(msg);
    }




}
