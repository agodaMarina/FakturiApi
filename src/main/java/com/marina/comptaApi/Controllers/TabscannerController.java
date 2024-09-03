package com.marina.comptaApi.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marina.comptaApi.Models.Ocr.TabPostResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("tabscanner")
public class TabscannerController {

    private static final String API_URL = "https://api.tabscanner.com/api/2/process";//post
    private static final String API="https://api.tabscanner.com/api/result"; //get
    private static final String API_KEY = "qsboUr62XUm55Mo9Q4RR64Bqb5RtZ0SfEKBSWxOLgpTjT8RVTXt6eiJewQl7bHRx";

    @PostMapping("/process")
    public ResponseEntity<?> processImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            // Sauvegarde le fichier temporairement
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(convFile)) {
                fos.write(file.getBytes());
            }
            // Vérifier et redimensionner l'image si nécessaire
            BufferedImage image = ImageIO.read(convFile);
            String formatName = getFormatName(convFile);
            if (image.getWidth() < 720 || image.getHeight() < 1280) {
                image = resizeImage(image, 720, 1280);
                ImageIO.write(image, formatName, convFile);
            }

            // Envoit le fichier à l'API Tabscanner
            String response = sendToTabscanner(convFile);


            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    //fonction pour redimentionner l'image
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }
    //récupérer le format de l'image
    private String getFormatName(File file) throws IOException {
        String formatName = "jpeg";  // Default format
        BufferedImage image = ImageIO.read(file);
        ImageIO.scanForPlugins();
        String[] formats = ImageIO.getReaderFormatNames();
        for (String format : formats) {
            if (ImageIO.getImageReadersByFormatName(format).hasNext()) {
                formatName = format;
                break;
            }
        }
        return formatName;
    }
    private String sendToTabscanner(File file) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadFile = new HttpPost(API_URL);
            uploadFile.addHeader("apikey", API_KEY);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("file", new FileBody(file));
            HttpEntity multipart = builder.build();

            uploadFile.setEntity(multipart);
            HttpResponse response = httpClient.execute(uploadFile);
            HttpEntity responseEntity = response.getEntity();

            //recupération du token dans la réponse envoyée par le post
            ObjectMapper mapper = new ObjectMapper();
            String responseString=EntityUtils.toString(responseEntity);
            TabPostResponse tabscannerResponse = mapper.readValue(responseString, TabPostResponse.class);
            String token = tabscannerResponse.getToken();

            //envoie dela requete get pour avoir le resultat de l'analyse :
            HttpGet getResults = new HttpGet(API + "/" + token);
            getResults.addHeader("apikey", API_KEY);
            HttpResponse response2=httpClient.execute(getResults);
            HttpEntity responseEntity2 = response2.getEntity();

            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity2);
            } else {
                throw new IOException("No response from Tabscanner API");
            }
        }
    }
}

