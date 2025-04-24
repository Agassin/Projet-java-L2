package src.controller;

import src.controller.lienAffaire;
import src.model.Affaire;
import src.model.Personne;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
            ajouterAffaire(new Affaire("Affaire 1", "Vol", null, null, "Paris", "Ouvert", "2023-01-01"));
            ajouterAffaire(new Affaire("Affaire 2", "Fraude", null, null, "Lyon", "Fermé", "2023-02-01"));
            ajouterAffaire(new Affaire("Affaire 3", "Cambriolage", null, null, "Marseille", "En cours", "2023-03-01"));
        }
    }

    public void ajouterAffaire(Affaire affaire) {
        if (affaire == null) {
            throw new IllegalArgumentException("L'affaire ne peut pas être null");
        }
        affaires.add(affaire);
    }

    public static List<Personne> lirePersonnesCSV(String fichier) throws IOException, CsvValidationException {
        List<Personne> personnes = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fichier))) {
            String[] nextLine;
            reader.readNext(); // Ignorer l'en-tête

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 6) continue;

                personnes.add(new Personne(
                        nextLine[0].trim(), // nom
                        nextLine[1].trim(), // prenom
                        nextLine[2].trim(), // quartier
                        nextLine[3].trim(), // profession
                        nextLine[4].trim(), // genre
                        nextLine[5].trim()  // antecedents
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
                if (nextLine.length < 7) continue;

                affaires.add(new Affaire(
                        nextLine[0].trim(), // nomAffaire
                        nextLine[1].trim(), // crime
                        trouverPersonneParNom(personnes, nextLine[2].trim()), // suspect
                        nextLine[3].isEmpty() ? null : trouverPersonneParNom(personnes, nextLine[3].trim()), // coupable
                        nextLine[4].trim(), // lieu
                        nextLine[5].trim(), // etat
                        nextLine[6].trim()  // date
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
        return new ArrayList<>(affaires);
    }

    public List<Personne> getPersonnes() {
        return new ArrayList<>(personnes);
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
    public lienAffaire trouverPersonnesLiees(Affaire affaire) {

        List<Personne> quartierList = new ArrayList<>();
        List<Personne> casierList = new ArrayList<>();

        String lieuAffaire = affaire.getLieu();         // ex: "Rocade"
        String crimeAffaire = affaire.getCrime();       // ex: "Vol"

        for (Personne p : personnes) {
            if (p.getQuartier().equalsIgnoreCase(lieuAffaire)) {
                quartierList.add(p);
            }
            if (p.getAntecedents().equalsIgnoreCase(crimeAffaire)) {
                casierList.add(p);
            }
        }

        return new lienAffaire(affaire,quartierList,casierList);
    }
}