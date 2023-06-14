package AG.test.com.openapi4.response;

public class KeycloakPublicKeyResponseBody
{
    private String realm;
    private String public_key;


    public String getRealm()
    {
        return realm;
    }

    public void setRealm(String realm)
    {
        this.realm = realm;
    }

    public String getPublic_key()
    {
        return public_key;
    }

    public void setPublic_key(String public_key)
    {
        this.public_key = public_key;
    }
}
