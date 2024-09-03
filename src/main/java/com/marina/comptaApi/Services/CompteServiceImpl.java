package com.marina.comptaApi.Services;
/*
public class CompteServiceImpl implements CompteService {




    @Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private CompteRepository compteRepository;

    public Facture traiterFacture(Facture facture) {
        // Logique de correspondance des comptes
        Compte compte = determinerCompte(facture);
        facture.setCompte(compte);

        return factureRepository.save(facture);
    }

    private Compte determinerCompte(Facture facture) {
        // Correspondance basée sur le fournisseur
        if (facture.getFournisseur().equalsIgnoreCase("XYZ")) {
            return compteRepository.findByNom("Achat");
        }
        // Correspondance basée sur le montant
        else if (facture.getMontant() < 100) {
            return compteRepository.findByNom("Petites dépenses");
        } else if (facture.getMontant() >= 100 && facture.getMontant() < 1000) {
            return compteRepository.findByNom("Dépenses moyennes");
        } else if (facture.getMontant() >= 1000) {
            return compteRepository.findByNom("Grandes dépenses");
        }
        // Correspondance basée sur des catégories
        else if (facture.getCategorie().equalsIgnoreCase("Informatique")) {
            return compteRepository.findByNom("Dépenses informatiques");
        } else if (facture.getCategorie().equalsIgnoreCase("Fournitures")) {
            return compteRepository.findByNom("Fournitures de bureau");
        }
        // Correspondance basée sur des mots-clés dans la description
        else {
            String description = facture.getDescription().toLowerCase();
            if (description.contains("ordinateur") || description.contains("laptop")) {
                return compteRepository.findByNom("Dépenses informatiques");
            } else if (description.contains("papier") || description.contains("stylo")) {
                return compteRepository.findByNom("Fournitures de bureau");
            }
        }
        // Retourner un compte par défaut si aucune règle ne correspond
        return compteRepository.findByNom("Divers");
    }
}



}
 */