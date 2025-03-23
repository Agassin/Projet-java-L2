package src.controller;

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

    public AffaireController() {
        affaires = new ArrayList<>();
    }

    public static Personne trouverPersonneParNom(List<Personne> personnes, String nom) {
        for (Personne p : personnes) {
            if ((p.getNom() + " " + p.getPrenom()).equalsIgnoreCase(nom)) {
                return p;
            }
        }
        return null; // Retourne null si la personne n'est pas trouvée
    }
    

    public static List<Personne> lirePersonnesCSV(String fichier) throws IOException, CsvValidationException {
        List<Personne> personnes = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(fichier));
        String[] nextLine;
        reader.readNext(); // Ignorer la ligne des entêtes
    
        while ((nextLine = reader.readNext()) != null) {
            String nom = nextLine[0];
            String prenom = nextLine[1];
            String quartier = nextLine[2];
            String profession = nextLine[3];
            String genre = nextLine[4];
            String antecedents = nextLine[5];
    
            Personne p = new Personne(nom, prenom, quartier, profession, genre, antecedents);
            personnes.add(p);
        }
        reader.close();
        return personnes;
    
    }

    public static List<Affaire> lireAffairesCSV(String fichier, List<Personne> personnes) throws IOException, CsvValidationException {
        List<Affaire> affaires = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(fichier));
        String[] nextLine;
        reader.readNext(); // Ignorer la première ligne (entêtes)
        while ((nextLine = reader.readNext()) != null) {
            String nomAffaire = nextLine[0];
            String crime = nextLine[1];
            Personne suspect = trouverPersonneParNom(personnes, nextLine[2]);
            Personne coupable = nextLine[3].isEmpty() ? null : trouverPersonneParNom(personnes, nextLine[3]); // Peut être null
            String lieu = nextLine[4];
            String etat = nextLine[5];
            String date = nextLine[6];
    
            affaires.add(new Affaire(nomAffaire, crime, suspect, coupable, lieu, etat, date));
        }
        reader.close();
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
                    System.out.println("Le coupable de " + nomAffaire + " est : " + affaire.getCoupable());
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

    

    public static void main(String[] args) throws CsvValidationException {
        try {
            List<Personne> personnes = lirePersonnesCSV("Personnes_Updated.csv");
            List<Affaire> affaires = lireAffairesCSV("Affaire_Updated.csv", personnes);
    
            // Afficher le coupable de l'affaire 1
            afficherCoupableAffaire("Affaire_1", affaires);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}


