package com.example.comptaApi.Models;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.Map;

/**
 * Uploads an image for receipt OCR and gets the result in JSON.
 * Required dependencies: org.apache.httpcomponents:httpclient:4.5.13 and org.apache.httpcomponents:httpmime:4.5.13
 */
public class JavaReceiptOcr {

    public static void main(String[] args) throws Exception {
        String receiptOcrEndpoint = "https://ocr.asprise.com/api/v1/receipt"; // Receipt OCR API endpoint
        File imageFile = new File("fac.jpg");



        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(receiptOcrEndpoint);
            post.setEntity(MultipartEntityBuilder.create()
                    .addTextBody("client_id", "TEST")       // Use 'TEST' for testing purpose
                    .addTextBody("recognizer", "auto")      // can be 'US', 'CA', 'JP', 'SG' or 'auto'
                    .addTextBody("ref_no", "ocr_java_123'") // optional caller provided ref code
                    .addPart("file", new FileBody(imageFile))    // the image file
                    .build());

            try (CloseableHttpResponse response = client.execute(post)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ObjectMapper objectMapper = new ObjectMapper();
                //objectMapper.readValue(jsonResponse, ReceiptResponse.class);


                Map receiptResponse = objectMapper.readValue(jsonResponse, Map.class);

                // Access the extracted data
                System.out.println("=== Informations relevées ===");
//                System.out.println("File Name: " + receiptResponse.getFile_name());
//                receiptResponse.getReceipts().forEach(receipt -> {
//                    System.out.println("Merchant Name: " + receipt.getMerchant_name());
//                    System.out.println("Date: " + receipt.getDate());
//                    System.out.println("Total: " + receipt.getTotal());
//                    // Add more fields as needed
//                });
            }
        }
    }
}
