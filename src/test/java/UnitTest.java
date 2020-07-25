import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sid.aws.signer.SignerV4;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

public class UnitTest {
    static private String endpoint = "https://endpoint.com/example/resource";
    static private String requestBody = "JSON_PAYLOAD";
    private static String secretKey = "AWS_SECRET_KEY";
    private static String accessKey = "AWS_ACCESS_KEY";
    private static String regionName = "AWS_REGION_NAME"; //"ap-south-1";
    private static String serviceName = "AWS_SERVICE_NAME";



    public static void main(String[] args) throws MalformedURLException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, UnirestException {
        SignerV4 awsSignerV4 = new SignerV4(secretKey, accessKey, regionName);
        Map<String, String> headers = awsSignerV4.getHeader(serviceName, endpoint, requestBody);
        
        HttpResponse<String> response = Unirest.post(endpoint)
                .headers(headers)
                .body(requestBody)
                .asString();

        System.out.println("response: " + response.getBody());
    }
}
