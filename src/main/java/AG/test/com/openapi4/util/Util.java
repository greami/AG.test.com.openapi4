package AG.test.com.openapi4.util;

import java.util.Base64;

public class Util
{


    public static String decodeUsernameAndPasswordFrom64(String authorization)
    {
        String usernameAndPassword64 = authorization.replace("Basic", " ").trim();
        byte[] usernameAndPasswordByte =  Base64.getDecoder().decode(usernameAndPassword64);
        return  new String(usernameAndPasswordByte);
    }


    public static String getUsernameFromUsernameAndPassword(String usernameAndPassword )
    {
        String[] usernameAndPasswordArray = usernameAndPassword.split(":");
        return usernameAndPasswordArray[0];
    }


    public static String getPasswordFromUsernameAndPassword(String usernameAndPassword )
    {
        String[] usernameAndPasswordArray = usernameAndPassword.split(":");
        return usernameAndPasswordArray[1];
    }

}
