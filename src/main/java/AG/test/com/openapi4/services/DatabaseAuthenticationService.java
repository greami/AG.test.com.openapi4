package AG.test.com.openapi4.services;

import AG.test.com.openapi4.exception.DatabaseAuthException;
import AG.test.com.openapi4.exception.InternalServerException;
import AG.test.com.openapi4.exception.ValidationException;
import AG.test.com.openapi4.validator.ValidateBasicAuthorization;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class DatabaseAuthenticationService
{

    private static String url = "http://localhost:8085/api3/inlog";
    private static String Authorization = "Authorization";

    public static void checkDatabaseAuth(String authorization) throws ValidationException
    {

/*        try
        {

            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String appConfigPath = rootPath + "application.properties";


            String rootPath = Thread.currentThread().getContextClassLoader().getResource("application.properties").getPath();
            FileInputStream fis = new FileInputStream(rootPath);

            Properties prop=new Properties();
            prop.load(fis);


            //String _Authorization = p.getProperty("header.Message");
            //String _url = p.getProperty("header.Authorization");
        }
        catch (Exception e )
        {
            System.out.println(e.getMessage());
        }*/

        ValidateBasicAuthorization.validate(authorization);

        try
        {


            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders reqHeader = new HttpHeaders();
            reqHeader.add(Authorization, authorization);

            MultiValueMap<String, String> reqBody = new LinkedMultiValueMap<>();
            HttpEntity<MultiValueMap<String,String>> reqest = new HttpEntity<>(reqBody, reqHeader);

            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.GET, reqest, Void.class);

            if(response.getStatusCode() == HttpStatus.UNAUTHORIZED)
            {
                throw new DatabaseAuthException();
            }

            if(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
            {
                throw new InternalServerException();
            }

        }
        catch (Exception e)
        {
            throw new InternalServerException();
        }
    }
}
