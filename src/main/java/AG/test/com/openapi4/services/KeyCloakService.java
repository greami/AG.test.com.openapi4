package AG.test.com.openapi4.services;

import AG.test.com.openapi4.exception.InternalServerException;
import AG.test.com.openapi4.exception.JWTnotValidException;
import AG.test.com.openapi4.exception.PublicKeyException;
import AG.test.com.openapi4.exception.ValidationException;
import AG.test.com.openapi4.response.KeyCloakJWTResponseBody;
import AG.test.com.openapi4.response.KeycloakPublicKeyResponseBody;
import AG.test.com.openapi4.util.Util;
import AG.test.com.openapi4.validator.ValidateBasicAuthorization;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

public class KeyCloakService
{
    //@Value("${KeyCloakService.url.getJWT}")
    public static String urlGetJWT = "http://localhost:8080/realms/myrealm/protocol/openid-connect/token";
//    @Value("${KeyCloakService.client_id}")
    private static String client_id = "client_id";
//    @Value("${KeyCloakService.username}")
    private static String username = "username";
//    @Value("${KeyCloakService.password}")
    private static String password = "password";
//    @Value("${KeyCloakService.grant_type}")
    private static String grant_type = "grant_type";
//    @Value("${KeyCloakService.client_id_value}")
    private static String client_id_value = "myclient";
//    @Value("${KeyCloakService.grant_type_value}")
    private static String grant_type_value= "password";

    private static String urlGetPublicKey= "http://localhost:8080/realms/myrealm/";

    public static String getJWT(String authorization) throws ValidationException
    {
        ValidateBasicAuthorization.validate(authorization);

        String usernameAndPassword = Util.decodeUsernameAndPasswordFrom64(authorization);
        String username_val = Util.getUsernameFromUsernameAndPassword(usernameAndPassword);
        String password_val = Util.getPasswordFromUsernameAndPassword(usernameAndPassword);

        try
        {
            RestTemplate restTemplate =  new RestTemplate();

            HttpHeaders reqHeader = new HttpHeaders();
            reqHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> reqBody = new LinkedMultiValueMap<>();
            reqBody.add(client_id, client_id_value  );
            reqBody.add(username, username_val);
            reqBody.add(password, password_val);
            reqBody.add(grant_type, grant_type_value);


            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(reqBody, reqHeader);

            ResponseEntity<KeyCloakJWTResponseBody> response = restTemplate.exchange(urlGetJWT, HttpMethod.POST, request, KeyCloakJWTResponseBody.class);

            if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
            {
                return response.getBody().getAccess_token();
            }

        }
        catch (Exception e)
        {
            throw new InternalServerException();
        }

        throw new InternalServerException();
    }


    public static String getPublicKey() throws ValidationException
    {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders reqHeader = new HttpHeaders();
        MultiValueMap<String, String> reqBody = new LinkedMultiValueMap<>();

        try
        {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(reqBody, reqHeader);

            ResponseEntity<KeycloakPublicKeyResponseBody> response = restTemplate.exchange(
                    urlGetPublicKey, HttpMethod.GET, request, KeycloakPublicKeyResponseBody.class );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null)
            {
                return response.getBody().getPublic_key();
            }

            if(response.getStatusCode()!= HttpStatus.OK)
            {
                throw new PublicKeyException();
            }

        }
        catch (Exception e)
        {
            throw new InternalServerException();
        }

        throw new InternalServerException();
    }


    public static void validateJWTwithPublicKey(String publicKey, String JWT) throws ValidationException
    {
        if( publicKey.isEmpty() || publicKey.isBlank() || JWT.isBlank() || JWT.isEmpty() )
        {
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            throw new JWTnotValidException();
        }

        Jws<Claims> jws;

        try
        {
            byte[] decode = Base64.decodeBase64(publicKey);
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(decode);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpecX509);

            jws =        Jwts.parserBuilder()                // (1)
                        .setSigningKey(pubKey)          // (2)
                        .build()                        // (3)
                        .parseClaimsJws(JWT);           // (4)
        }  catch (Exception e)
        {
            throw new JWTnotValidException();
        }
    }


}
