package src.Controller;

import src.Model.Affaire;
import src.Model.Personne;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AffaireController {
    private List<Affaire> affaires;

    public AffaireController() {
        affaires = new ArrayList<>();
    }

    public static Personne trouverPersonneParNom(List<Personne> personnes, String nom) {
        for (Personne p : personnes) {
            if (p.getNomComplet().equalsIgnoreCase(nom)) {
                return p;
            }
        }
        return null; // Retourne null si la personne n'est pas trouvée
    }

    public static List<Personne> lirePersonnesCSV(String fichier) {
        List<Personne> personnes = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fichier))) {
            String[] nextLine;
            reader.readNext(); // Ignorer la ligne des entêtes

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 6) continue; // Vérifier qu'on a bien toutes les colonnes

                String nom = nextLine[0].trim();
                String prenom = nextLine[1].trim();
                String quartier = nextLine[2].trim();
                String profession = nextLine[3].trim();
                String genre = nextLine[4].trim();
                String antecedents = nextLine[5].trim();

                personnes.add(new Personne(nom, prenom, quartier, profession, genre, antecedents));
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Erreur lors de la lecture du fichier " + fichier + " : " + e.getMessage());
        }
        return personnes;
    }

    public static List<Affaire> lireAffairesCSV(String fichier, List<Personne> personnes) {
        List<Affaire> affaires = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fichier))) {
            String[] nextLine;
            reader.readNext(); // Ignorer la première ligne (entêtes)

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 7) continue; // Vérifier qu'on a bien toutes les colonnes

                String nomAffaire = nextLine[0].trim();
                String crime = nextLine[1].trim();
                Personne suspect = trouverPersonneParNom(personnes, nextLine[2].trim());
                Personne coupable = nextLine[3].isEmpty() ? null : trouverPersonneParNom(personnes, nextLine[3].trim());
                String lieu = nextLine[4].trim();
                String etat = nextLine[5].trim();
                String date = nextLine[6].trim();

                affaires.add(new Affaire(nomAffaire, crime, suspect, coupable, lieu, etat, date));
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Erreur lors de la lecture du fichier " + fichier + " : " + e.getMessage());
        }
        return affaires;
    }

    public void ajouterAffaire(Affaire affaire) {
        if (affaires == null) {
            affaires = new ArrayList<>();
        }
        affaires.add(affaire);
    }

    public static void afficherCoupableAffaire(String nomAffaire, List<Affaire> affaires) {
        for (Affaire affaire : affaires) {
            if (affaire.toString().contains(nomAffaire)) {
                if (affaire.getCoupable() != null) {
                    System.out.println("Le coupable de " + nomAffaire + " est : " + affaire.getCoupable().getNomComplet());
                } else {
                    System.out.println("Aucun coupable trouvé pour " + nomAffaire);
                }
                return;
            }
        }
        System.out.println("Affaire non trouvée !");
    }

    public List<Affaire> getAffaires() {
        return affaires != null ? affaires : new ArrayList<>();
    }

    public static void main(String[] args) {
        try {
            List<Personne> personnes = lirePersonnesCSV("src/BDD/Personnes.csv");
            List<Affaire> affaires = lireAffairesCSV("src/BDD/Affaire.csv", personnes);

            System.out.println("Personnes chargées : " + personnes.size());
            System.out.println("Affaires chargées : " + affaires.size());
            System.out.println(personnes);

            afficherCoupableAffaire("Affaire_1", affaires);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
