package com.epam;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateFactory;


public class TokenValidator {

    static String certificate = "MIIDBjCCAe4CCQDmPif23IJerzANBgkqhkiG9w0BAQUFADBFMQswCQYDVQQGEwJBVTETMBEGA1UECAwKU29tZS1TdGF0ZTEhMB8GA1UECgwYSW50ZXJuZXQgV2lkZ2l0cyBQdHkgTHRkMB4XDTE1MDkyMjA3MDkwMFoXDTE2MDkyMTA3MDkwMFowRTELMAkGA1UEBhMCQVUxEzARBgNVBAgMClNvbWUtU3RhdGUxITAfBgNVBAoMGEludGVybmV0IFdpZGdpdHMgUHR5IEx0ZDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALYjKc8pLFkAa45j6w6PHsroe7ijOCfhZmVtMCvZ8lINaP9mR8irOJHpLdJs4vpbxEZMqqLMhKjO7iUmXBmml37QRlJXY6f25essPkTdUmhiIrU/rIrZrCanvegXUHkvf4xvOQ1BTx/p5b1iIq3Wrk5Fox3pMigzqYhk4YuiJho8uabC9zyecmS3zIoRgwx+Vacel/ZW6r6YOlB6mblN9IvasvqWgDalegmMKOIZvwkpo/3mfzcGi5haWZZ3ufUqQjb4B7raJmfyrLnwi6XI9UzzGc04pCfIAsxTb5yM8cJQcJ/5VHF3h21eFJdZKyD2210gSq7/Y8Oda0dDXQchmFcCAwEAATANBgkqhkiG9w0BAQUFAAOCAQEAmBm84PfwOe5sGemT9D4ImIYWp/1xrV6b0zeaDZ6Yi7sz9R3VKjfnAxUVOHA7GlMi8xHHdDu7VhVhAWyFCvITdG00K18xJuKap1bwzw3kr70lLQaFahV+zaWKfWAfV34tots3G/hMqdOv0a+I/5t/T7oKPCmm/IfCVKdC1tGbTji+hxVLpaAkn60RFNzLKGFwtSxv9ObxR5Hn88+wV48VAcEnwcUk2DjBi1fW6jnMcNJbVd+/oKBOwj7UK2Lk10Qaeet8KKh5fFKEpgx7D4ITwer0G/Je1NMv1/lfNzpKlTKoBureF5C6B+rJIesQ/dAfg6H/ggxbgVMuo6imIPVvrg==";
    static String signedData = "Z5MwwjtXdypMQGNwmNNuCVmRcDVT24EgtwoDWalF4icxwz7jyB99Yg3262D7OsERewv4cOfdEz3bbOF-iG7YWXeSC9YZeO1tGapqlc8FRtAergSUZC7BcbFEx75MfSy7qLWYTOfdpJesQ23rOzjF7KdrAMJC_Y0T_r6RuBcZVyfT4P55kICETYyv7bBDXc9V8BJUf-QHDu6DaH7u6PSyeOmdzFInI_LwySnMlr3VahoUfUpJmauU8yHQUnFakJBgrMBe1Au9tS-HxtDVnHmoHQw8xGXsVQnEOa1aPAcVWy0v7hILUSmWNAG3IZ0JwUztQitgtnTTzXDszUxTbJ4YlQ";

    static String data = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ik1uQ19WWmNBVGZNNXBPWWlKSE1iYTlnb0VLWSIsImtpZCI6Ik1uQ19WWmNBVGZNNXBPWWlKSE1iYTlnb0VLWSJ9.eyJhdWQiOiJodHRwczovL2NpdHJpeHAuY29tOjg0NDMvIiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvZGQ5YjZhM2UtMjlkMS00MjU0LWE3NDYtZTAyOTQxNDQ0NTE3LyIsImlhdCI6MTQ0MjYyNjA0NSwibmJmIjoxNDQyNjI2MDQ1LCJleHAiOjE0NDI2Mjk5NDUsInZlciI6IjEuMCIsInRpZCI6ImRkOWI2YTNlLTI5ZDEtNDI1NC1hNzQ2LWUwMjk0MTQ0NDUxNyIsIm9pZCI6ImJkZDNmZDAyLTEyMzMtNGMxOC05NTRmLWJkNGFjMWYzOWU5OSIsInVwbiI6InNwQGNpdHJpeHAuY29tIiwic3ViIjoieUFfZTFzMmFOeFdvR3NSNzhfVWNYZkk5dERlYUM0QUozdXFZQXFDdllSbyIsImdpdmVuX25hbWUiOiJzIiwiZmFtaWx5X25hbWUiOiJwIiwibmFtZSI6InNwIiwiYW1yIjpbInB3ZCIsInJzYSJdLCJ1bmlxdWVfbmFtZSI6InNwQGNpdHJpeHAuY29tIiwiYXBwaWQiOiIyOWQ5ZWQ5OC1hNDY5LTQ1MzYtYWRlMi1mOTgxYmMxZDYwNWUiLCJhcHBpZGFjciI6IjAiLCJzY3AiOiJtZG1fZGVsZWdhdGlvbiIsImFjciI6IjEiLCJpcGFkZHIiOiI2My4xMTAuNTEuMTEiLCJkZXZpY2VpZCI6ImQyNzU3NzY0LWFiOTEtNDBiMS05MmM2LTViOWE4MWYxODNiYyJ9";


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String certString = "-----BEGIN CERTIFICATE-----\r\n" + certificate + "\r\n-----END CERTIFICATE-----";
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        CertificateFactory cf;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream stream = new ByteArrayInputStream(certString.getBytes()); //StandardCharsets.UTF_8
            java.security.cert.Certificate cert = cf.generateCertificate(stream);
            PublicKey pk = cert.getPublicKey();


            //verifying content with signature and content :

            byte[] signature = org.apache.commons.codec.binary.Base64.decodeBase64(signedData);
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify( pk );
            sig.update(data.getBytes());
            Boolean ret = sig.verify(signature);
            System.out.println(ret);
        } catch ( SignatureException | NoSuchAlgorithmException | InvalidKeyException | java.security.cert.CertificateException e) {
            e.printStackTrace();
        }
    }

}
