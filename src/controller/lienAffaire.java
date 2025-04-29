package src.controller;

import src.model.Affaire;
import src.model.Personne;
import java.util.List;

public class lienAffaire {
    public Affaire laffaire;
    private List<Personne> personnesMemeQuartier;
    private List<Personne> personnesMemeCasier;

    public lienAffaire(Affaire laffaire,List<Personne> quartier, List<Personne> casier) {
        this.laffaire = laffaire;
        this.personnesMemeQuartier = quartier;
        this.personnesMemeCasier = casier;
    }

    public List<Personne> getPersonnesMemeQuartier() {
        return personnesMemeQuartier;
    }

    public List<Personne> getPersonnesMemeCasier() {
        return personnesMemeCasier;
    }
}
