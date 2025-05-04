package src.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import src.model.ResultatAlgo;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultAlgoController {
    private List<ResultatAlgo> crimesSimilaires;

    public ResultAlgoController() {
        this.crimesSimilaires = new ArrayList<>();
        chargerCrimesSimilaires("src/BDD/CrimesSimilaires.csv");
    }

    private void chargerCrimesSimilaires(String cheminCSV) {
        try (CSVReader reader = new CSVReader(new FileReader(cheminCSV))) {
            String[] ligne;
            reader.readNext(); // Ignorer l'en-tête

            while ((ligne = reader.readNext()) != null) {
                if (ligne.length < 5) continue;

                crimesSimilaires.add(new ResultatAlgo(
                        ligne[0].trim(), // prénom
                        ligne[1].trim(), // nom
                        ligne[2].trim(), // antécédents
                        Double.parseDouble(ligne[3].trim()), // similarité
                        ligne[4].trim()  // description
                ));
            }

        } catch (IOException | CsvValidationException | NumberFormatException e) {
            System.err.println("Erreur de lecture du CSV des crimes similaires : " + e.getMessage());
        }
    }

    public List<ResultatAlgo> getCrimesSimilaires() {
        return Collections.unmodifiableList(crimesSimilaires);
    }

    // Exemple d'affichage
    public void afficherCrimes() {
        for (ResultatAlgo c : crimesSimilaires) {
            System.out.println(c);
        }
    }
}
