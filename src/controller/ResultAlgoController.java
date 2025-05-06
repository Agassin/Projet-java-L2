package src.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import src.model.ResultatAlgo;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResultAlgoController {
    private List<ResultatAlgo> crimesSimilaires;
    private static final String CHEMIN_CSV = "src/controller/resultats_affaire.csv";

    public ResultAlgoController() {
        this.crimesSimilaires = new ArrayList<>();
        chargerCrimesSimilaires();
    }

    private void chargerCrimesSimilaires() {
        try (CSVReader reader = new CSVReader(new FileReader(CHEMIN_CSV))) {
            String[] ligne;
            reader.readNext(); // Ignorer l'en-tête

            while ((ligne = reader.readNext()) != null) {
                if (ligne.length < 5) continue;

                try {
                    ResultatAlgo resultat = new ResultatAlgo(
                            ligne[0].trim(), // prénom
                            ligne[1].trim(), // nom
                            ligne[2].trim(), // antécédents
                            Double.parseDouble(ligne[3].trim()), // similarité
                            ligne[4].trim()  // description
                    );
                    crimesSimilaires.add(resultat);
                } catch (NumberFormatException e) {
                    System.err.println("Erreur de format pour la similarité: " + ligne[3]);
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Erreur de lecture du CSV: " + e.getMessage());
        }
    }

    public List<ResultatAlgo> getCrimesSimilaires() {
        return Collections.unmodifiableList(crimesSimilaires);
    }

    public List<ResultatAlgo> getCrimesTriesParSimilarite() {
        return crimesSimilaires.stream()
                .sorted(Comparator.comparingDouble(ResultatAlgo::getSimilarite).reversed())
                .collect(Collectors.toList());
    }

    public ResultatAlgo getResultatPourAffaire(String nomAffaire) {
        return crimesSimilaires.stream()
                .filter(r -> r.getNom().equalsIgnoreCase(nomAffaire))
                .findFirst()
                .orElse(null);
    }

    public String getDescriptionPourAffaire(String nomAffaire) {
        ResultatAlgo resultat = getResultatPourAffaire(nomAffaire);
        return resultat != null ? resultat.getDescriptionCrime() : "Aucune description disponible";
    }

    public void afficherResultats() {
        if (crimesSimilaires.isEmpty()) {
            System.out.println("Aucun résultat disponible.");
            return;
        }

        System.out.println("\n=== Résultats des crimes similaires ===");
        crimesSimilaires.forEach(System.out::println);
    }

    public void rechargerDonnees() {
        crimesSimilaires.clear();
        chargerCrimesSimilaires();
    }
}