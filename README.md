# aws-signature-version-4
Signature Version 4 is the process to add authentication information to AWS requests sent by HTTP. 
<br/>
For security, most requests to AWS must be signed with an access key, which consists of an access key ID and secret access key. These two keys are commonly referred to as your security credentials.
</br>

<p>When you use the <a href="https://aws.amazon.com/cli/" target="_blank"><span>AWS Command Line Interface (AWS CLI)</span><awsui-icon class="awsdocs-link-icon" name="external" initialized="true"><span class="awsui-icon awsui-icon-size-normal awsui-icon-variant-normal"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" focusable="false" aria-hidden="true"><path class="stroke-linecap-square" d="M10 2h4v4"></path><path d="M6 10l8-8"></path><path class="stroke-linejoin-round" d="M14 9.048V14H2V2h5"></path></svg></span></awsui-icon></a> or one of the
                                          <a href="https://aws.amazon.com/tools/" target="_blank"><span>AWS SDKs</span><awsui-icon class="awsdocs-link-icon" name="external" initialized="true"><span class="awsui-icon awsui-icon-size-normal awsui-icon-variant-normal"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" focusable="false" aria-hidden="true"><path class="stroke-linecap-square" d="M10 2h4v4"></path><path d="M6 10l8-8"></path><path class="stroke-linejoin-round" d="M14 9.048V14H2V2h5"></path></svg></span></awsui-icon></a> to make requests to AWS, these tools
                                          automatically sign the requests for you with the security credentials you specify
                                          when you
                                          configure the tools. When you use these tools, you don't need to learn how to sign
                                          requests
                                          yourself. <b>However, when you manually create HTTP requests to access AWS services,
                                          you must sign requests
                                          that require signing yourself.</b>
                                       </p>

<b>How to use this small lib to implement Signature Version 4</b>

It will give you prepared header which you can use to call your AWS API. 
Below I have used unirest java lib to call POST API. You can use of your choice.

```
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

```
