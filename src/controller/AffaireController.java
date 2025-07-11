package src.controller;


import src.model.Affaire;
import src.model.Personne;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AffaireController {
    private List<Affaire> affaires;
    private List<Personne> personnes;

    public AffaireController() {
        this.affaires = new ArrayList<>();
        this.personnes = new ArrayList<>();
        chargerDonneesInitiales();
    }

    private void chargerDonneesInitiales() {
        // Chargement depuis CSV
        try {
            this.personnes = lirePersonnesCSV("src/BDD/Personnes.csv");
            this.affaires = lireAffairesCSV("src/BDD/Affaire.csv", this.personnes);
        } catch (Exception e) {
            System.err.println("Erreur chargement données CSV: " + e.getMessage());
            // Données de test en cas d'échec
        }
    }

    public void ajouterAffaire(Affaire affaire) {
        if (affaire == null) {
            throw new IllegalArgumentException("L'affaire ne peut pas être null");
        }
        this.affaires.add(affaire);
    }

    public void ajouterPersonne(Personne personne) {
        if (personne == null) {
            throw new IllegalArgumentException("La personne ne peut pas être null");
        }
        this.personnes.add(personne);
    }

    public boolean supprimerAffaire(String nomAffaire) {
        return affaires.removeIf(a -> a.getNomAffaire().equalsIgnoreCase(nomAffaire));
    }

    public boolean modifierAffaire(String nomAffaire, Affaire nouvelleAffaire) {
        for (int i = 0; i < affaires.size(); i++) {
            if (affaires.get(i).getNomAffaire().equalsIgnoreCase(nomAffaire)) {
                affaires.set(i, nouvelleAffaire);
                return true;
            }
        }
        return false;
    }

    public boolean supprimerPersonne(String nomComplet) {
        return personnes.removeIf(p -> p.getNomComplet().equalsIgnoreCase(nomComplet));
    }

    public boolean modifierPersonne(String nomComplet, Personne nouvellePersonne) {
        for (int i = 0; i < personnes.size(); i++) {
            if (personnes.get(i).getNomComplet().equalsIgnoreCase(nomComplet)) {
                personnes.set(i, nouvellePersonne);
                return true;
            }
        }
        return false;
    }

    public static List<Personne> lirePersonnesCSV(String fichier) throws IOException, CsvValidationException {
        List<Personne> personnes = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fichier))) {
            String[] nextLine;
            reader.readNext(); // Ignorer l'en-tête

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 7) continue;

                personnes.add(new Personne(
                        nextLine[0].trim(), // nom
                        nextLine[1].trim(), // prenom
                        nextLine[4].trim(), //age
                        nextLine[5].trim(), // quartier
                        nextLine[6].trim(), // profession
                        nextLine[3].trim(), // genre
                        nextLine[8].trim(),  // antecedents
                        nextLine[7].trim()  // description
                ));
            }
        }
        return personnes;
    }

    public static List<Affaire> lireAffairesCSV(String fichier, List<Personne> personnes) throws IOException, CsvValidationException {
        List<Affaire> affaires = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fichier))) {
            String[] nextLine;
            reader.readNext(); // Ignorer l'en-tête

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 8) continue;

                affaires.add(new Affaire(
                        nextLine[0].trim(), // nomAffaire
                        nextLine[1].trim(), // crime
                        trouverPersonneParNom(personnes, nextLine[2].trim()), // suspect
                        nextLine[3].isEmpty() ? null : trouverPersonneParNom(personnes, nextLine[3].trim()), // coupable
                        nextLine[4].trim(), // lieu
                        nextLine[5].trim(), // etat
                        nextLine[6].trim(),  // date
                        nextLine[7].trim()   // description
                ));
            }
        }
        return affaires;
    }

    public static Personne trouverPersonneParNom(List<Personne> personnes, String nom) {
        if (nom == null || nom.trim().isEmpty()) return null;

        return personnes.stream()
                .filter(p -> p.getNomComplet().equalsIgnoreCase(nom))
                .findFirst()
                .orElse(null);
    }

    public List<Affaire> getAffaires() {

        return Collections.unmodifiableList(affaires);
    }

    public List<Personne> getPersonnes() {
        return Collections.unmodifiableList(personnes); // Ou simplement return personnes;
    }

    public void afficherCoupableAffaire(String nomAffaire) {
        affaires.stream()
                .filter(a -> a.getNomAffaire().equalsIgnoreCase(nomAffaire))
                .findFirst()
                .ifPresentOrElse(
                        a -> {
                            if (a.getCoupable() != null) {
                                System.out.println("Coupable: " + a.getCoupable().getNomComplet());
                            } else {
                                System.out.println("Aucun coupable trouvé");
                            }
                        },
                        () -> System.out.println("Affaire non trouvée")
                );
    }

    public int getNbAffairesParStatut(String etat){
        int cptOuv = 0;
        int cptFerm = 0;
        int cptUrg = 0;
        int resultat = 0;
        for(Affaire a : affaires){
            if(a.getEtat().equals("En cours")){
                cptOuv++;

            }
            if(a.getEtat().equals("Fermée")){
                cptFerm++;

            }
            if(a.getEtat().equals("Urgente")){
                cptUrg++;

            }

        }

        if(etat == "En cours"){
            resultat = cptOuv;
        }else if(etat == "Fermée"){
            resultat = cptFerm;
        }else if(etat == "Urgente") {
            resultat = cptUrg;
        }

        return resultat ;
    }


    public int getPourcentageAffaire(String etat){
        int a = getNbAffairesParStatut(etat);
        int b  = affaires.size();
        return (a*100) / b;
    }
}