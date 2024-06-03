package com.marina.comptaApi.Controllers;

import java.io.BufferedWriter;
import java.io.File;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Uploads an image for receipt OCR and gets the result in JSON.
 * Required dependencies: org.apache.httpcomponents:httpclient:4.5.13 and org.apache.httpcomponents:httpmime:4.5.13
 */
public class JavaReceiptOcr {

    public static void main(String[] args) throws Exception {
        String receiptOcrEndpoint = "https://ocr.asprise.com/api/v1/receipt"; // Receipt OCR API endpoint
        File imageFile = new File("fac.jpg");

        System.out.println("=== Informations relevées ===");

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(receiptOcrEndpoint);
            post.setEntity(MultipartEntityBuilder.create()
                    .addTextBody("client_id", "TEST")       // Use 'TEST' for testing purpose
                    .addTextBody("recognizer", "auto")      // can be 'US', 'CA', 'JP', 'SG' or 'auto'
                    .addTextBody("ref_no", "ocr_java_123'") // optional caller provided ref code
                    .addPart("file", new FileBody(imageFile))    // the image file
                    .build());

            try (CloseableHttpResponse response = client.execute(post)) {
//                File info= new File("test.txt");
//                BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(info));
//
//                bufferedWriter.write(EntityUtils.toString(response.getEntity()));
//                bufferedWriter.close();
                System.out.println(EntityUtils.toString(response.getEntity())); // Receipt OCR result in JSON
            }
        }
    }
}