package com.marina.comptaApi.Services;

import com.lowagie.text.DocumentException;
import com.marina.comptaApi.Models.Achat;
import com.marina.comptaApi.Models.Rapport;
import com.marina.comptaApi.Models.Solde;
import com.marina.comptaApi.Repositories.AchatRepository;
import com.marina.comptaApi.Repositories.SoldeHistoriqueRepository;
import com.marina.comptaApi.Repositories.SoldeRepository;
import com.marina.comptaApi.auth.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RapportService {

     private final AchatRepository depenseRepository;
     private final SoldeRepository soldeRepository;
     private final SoldeHistoriqueRepository soldeHistoriqueRepository;
     private final AuthenticationService authservice;
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private final String QUICKCHART_API_URL = "https://quickchart.io/chart"; //  URL de l'API QuickChart
    private final RestTemplate restTemplate = new RestTemplate();


    public String generateBarChart(Rapport reportData) {

        // 1. Préparez les données JSON pour le graphique QuickChart
        //    (Voir exemples dans la documentation de QuickChart :  https://quickchart.io/documentation/)

        String chartJson = "{ " +
                "\"type\": \"bar\", " +
                "\"data\": { " +
                "\"labels\": " + this.convertKeysToStringArray(reportData.getDepensesParCategorie().keySet()) + ", " +
                "\"datasets\": [{" +
                "\"label\": \"Dépenses par catégorie\", " +
                "\"data\": " +  this.convertValuesToDoubleArray(reportData.getDepensesParCategorie().values())  +
                "}" +
                "]" +
                "}," +
                "\"options\": { /*  ...  */ } " + // (Options de personnalisation du graphique)
                "}";

        // 2.  Configurez l'en-tête  HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //  3. Créez l'entité de la requête
        HttpEntity<String> requestEntity = new HttpEntity<>(chartJson, headers);

        // 4. Appelez l'API QuickChart
        String chartUrl = restTemplate.postForObject(QUICKCHART_API_URL, requestEntity, String.class);

        return chartUrl;  //  Retournez l'URL du graphique
    }

    //Convertion du type map<LocalDate,double> en string[]
    public String convertKeysToStringArray(java.util.Set<String> keys) {
        // Créez un nouveau tableau String avec la même taille que le set
        String[] stringArray = new String[keys.size()];
        // Copiez les éléments du set dans le tableau
        return  "[" +  String.join(", ", keys.toArray(stringArray)) + "]";
    }

    // Convertion des valeurs du map en  tableau Double []
    public String convertValuesToDoubleArray(java.util.Collection<Double> values){
        return  "[" + values.stream().map(String::valueOf).collect(Collectors.joining(",")) + "]";
    }


    public Rapport generateMonthlyReportData(int year, int month) {

        // 1. Calcul des dates de début et de fin du mois
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        // 2. Récupération des dépenses et des revenus  pour la période
        //List<Achat> depenses = depenseRepository.findByDateEmissionBetween(startDate, endDate);
        System.out.println("depenses : " +endDate.toString()+" "+ startDate.toString());

        // 3.  Calcul du solde initial  (à adapter selon vos besoins)
         //Solde soldeInitial = soldeHistoriqueRepository.findByUtilisateurAndDateLessThanEqual(authservice.getProfile(), startDate);

         //if (soldeInitial == null) {
              // Gérer le cas où aucun solde initial n'est trouvé pour l'utilisateur
          //    throw new RuntimeException("Aucun solde initial trouvé pour l'utilisateur."); // Exemple d'exception
       //  }
         double soldeInitialMontant = 1000;
                 //soldeInitial.getSolde();

         //4. solde final
        double soldeFinal=21500;
                //soldeRepository.findByDate(endDate).getSolde();
/*
         // 4. Calcul du total des dépenses
        double totalDepenses = depenses.stream()
                .mapToDouble(Achat::getTotalttc)
                .sum();

        // 5. Préparation des données pour le graphique d'évolution
        Map<LocalDate, Double> depensesParJour = depenses.stream()
                .collect(Collectors.groupingBy(Achat::getDateEmission,
                        Collectors.summingDouble(Achat::getTotalttc)));

        // 6.  Préparation des données pour le graphique de répartition par catégories
        Map<String, Double> depensesParCategorie = depenses.stream()
                .collect(Collectors.groupingBy(d -> d.getCategorie().getLibelle(),
                        Collectors.summingDouble(Achat::getTotalttc)));

        // 7. Création et retour de l'objet ReportData
       // return new Rapport(startDate, endDate, totalDepenses, depenses,
        //        depensesParJour, depensesParCategorie);

        return Rapport.builder()
                .startDate(startDate)
                .endDate(endDate)
                .soldeInitial(soldeInitialMontant)
                .soleFinal(soldeFinal)
                .totalDepenses(totalDepenses)
                .depenses(depenses)
                .depensesParJour(depensesParJour)
                .depensesParCategorie(depensesParCategorie)
                .build();
                */
        return null;
    }

    public void generateAndSendMonthlyReport(int year, int month) throws IOException {

        // 1. Générer les données du rapport
        Rapport reportData = generateMonthlyReportData(year, month);
        var user=authservice.getProfile();

        //  Générer l'URL du graphique
        String chartUrl = generateBarChart(reportData);


        // 2. Préparez le contexte Thymeleaf
        Context context = new Context();
        context.setVariable("reportData", reportData);
        context.setVariable("chartUrl", chartUrl);
        context.setVariable("reportId", "INV-" +  year + "-" +  month);  // Générez un ID unique pour le rapport
        context.setVariable("userName", user.getUsername()); // Récupérez le nom d'utilisateur
        context.setVariable("userEmail", user.getEmail()); // Récupérez l'email de l'utilisateur


        // 3.  Générez le HTML avec Thymeleaf
        String htmlContent = templateEngine.process("rapport", context);

        // 4.  Convertissez le HTML en PDF avec Flying Saucer
        byte[] pdfBytes = generatePdfFromHtml(htmlContent);

        // 5.  Envoyez le rapport par mail
        sendEmailWithReport(user.getEmail(), pdfBytes);
    }

    // Méthode pour générer un PDF à partir du contenu HTML
    private byte[] generatePdfFromHtml(String htmlContent) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour envoyer un email avec le rapport PDF en pièce jointe
    private void sendEmailWithReport(String recipientEmail, byte[] pdfBytes) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setSubject("Votre rapport mensuel de dépenses");
            helper.setText("Bonjour,\n\nVeuillez trouver ci-joint votre rapport de dépenses mensuel.", false); // false pour le texte brut

            //  Attachez le rapport PDF
            helper.addAttachment("rapport-mensuel.pdf",
                    new ByteArrayResource(pdfBytes));

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
