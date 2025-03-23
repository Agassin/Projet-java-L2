import java.util.ArrayList;
import java.util.List;

class AffaireController {
    private List<Affaire> affaires;

    public AffaireController() {
        affaires = new ArrayList<>();
    }

    public void ajouterAffaire(Affaire affaire) {
        affaires.add(affaire);
    }

    public List<Affaire> getAffaires() {
        return affaires;
    }
}
