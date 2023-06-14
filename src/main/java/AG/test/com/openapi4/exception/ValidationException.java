package AG.test.com.openapi4.exception;

public class ValidationException extends Exception
{

    String msg;


    public void setMsg(String msg)
    {
        this.msg = msg;
    }


    @Override
    public String getMessage()
    {
        return this.msg;
    }
}
