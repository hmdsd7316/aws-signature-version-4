package com.sid.aws.signer;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SignerV4 {
    private String secretKey;
    private String accessKey;
    private String regionName;
    private static String REQUEST_METHOD = "POST";
    private static String AUTH_ALGORITHM = "AWS4-HMAC-SHA256";
    private final static String REQUEST_CONTENT_TYPE = "application/json";

    public SignerV4(String secretKey, String accessKey, String regionName){
        this.secretKey = secretKey;
        this.accessKey = accessKey;
        this.regionName = regionName;
    }

    public Map<String, String> getHeader(String serviceName, String endpoint, String requestBody) throws UnsupportedEncodingException, NoSuchAlgorithmException, MalformedURLException, SignatureException, InvalidKeyException {
        URL url = new URL(endpoint);
        String host = url.getHost();
        String payloadHash = Hmac.getSha256Hash(requestBody);

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        String timestamp = getTimeStamp(now);
        String date = getDate(now);


        String authorization = "AWS4-HMAC-SHA256";
        authorization += " Credential=" + this.accessKey;
        authorization += "/" + date;
        authorization += "/" + this.regionName;
        authorization += "/" + serviceName;
        authorization += "/" + "aws4_request";
        authorization += ", " + "SignedHeaders=content-length;content-type;host;x-amz-date;x-api-key";

        String canonical_uri = url.getPath();
        String canonical_querystring = "";

        String canonical_headers = "";
        canonical_headers += "content-type:" + REQUEST_CONTENT_TYPE + "\n";
        canonical_headers += "host:" + host + "\n";
        canonical_headers += "x-amz-content-sha256:" + payloadHash + "\n";
        canonical_headers +=  "x-amz-date:" + timestamp + "\n";


        String signed_headers = "";
        signed_headers += 	"content-type";
        signed_headers +=	";host";
        signed_headers +=	";x-amz-content-sha256;";
        signed_headers +=	"x-amz-date";

        String canonical_request = "";
        canonical_request +=	REQUEST_METHOD + "\n";
        canonical_request +=	canonical_uri + "\n";
        canonical_request +=	canonical_querystring + "\n";
        canonical_request +=	canonical_headers + "\n";
        canonical_request +=	signed_headers + "\n";
        canonical_request +=	payloadHash;

        String credential_scope = "";
        credential_scope +=		date + "/" ;
        credential_scope +=		this.regionName + "/";
        credential_scope +=		serviceName	+ "/";
        credential_scope +=		"aws4_request";

        String canonical_request_hash = Hmac.getSha256Hash(canonical_request);

        String string_to_sign = "";
        string_to_sign +=		AUTH_ALGORITHM + "\n";
        string_to_sign +=		timestamp + "\n";
        string_to_sign += 		credential_scope + "\n";
        string_to_sign +=		canonical_request_hash;

        byte[] sigKey = generateAws4SigningKey(date, serviceName);


        String signature = Hmac.calculateHMAC(string_to_sign, sigKey, Hmac.HMAC_SHA256);
        String authorization_header = "";
        authorization_header = AUTH_ALGORITHM;
        authorization_header +=		" " + "Credential=" + this.accessKey + "/";
        authorization_header +=		credential_scope;
        authorization_header +=		", " + "SignedHeaders=" + signed_headers;
        authorization_header +=   	", " + "Signature=" + signature;



        System.out.println("authorization: " + authorization);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("x-api-key", this.accessKey);
        headerMap.put("host", url.getHost());
        headerMap.put("x-amz-date", timestamp);
        headerMap.put("X-Amz-Content-Sha256", payloadHash);
        headerMap.put("authorization", authorization_header);
        headerMap.put("content-type", "application/json");
        headerMap.put("cache-control", "no-cache");

        return headerMap;
    }

    private String getDate(OffsetDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatDateTime = dateTime.format(formatter);
        return formatDateTime;
    }

    private String getTimeStamp(OffsetDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        String formatDateTime = dateTime.format(formatter);
        return formatDateTime;
    }

    private byte[] generateAws4SigningKey(String timestamp, String serviceName) {

        byte[] signatureKey = null;
        try {
            signatureKey = SignatureKeyGenerator.generateSignatureKey(this.secretKey, timestamp, this.regionName,
                    serviceName);
        } catch (Exception e) {
            System.out.println("An error has ocurred when generate signature key: " + e);
        }

        return signatureKey;
    }
}
