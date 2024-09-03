package com.marina.comptaApi.Models.Ocr;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class JavaReceiptOcr {

    public void analyse(String[] args) throws Exception {
        String receiptOcrEndpoint = "https://api.tabscanner.com/api/2/process"; // Receipt OCR API endpoint
        File imageFile = new File("");



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
