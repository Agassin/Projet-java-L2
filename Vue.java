import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Vue extends Application {

    // Constructeur de la classe Vue (tu peux l'utiliser pour initialiser des variables ou des propriétés)
    public Vue() {
        System.out.println("Vue initialisée !");
    }

    @Override
    public void start(Stage primaryStage) {
        // Titre de la fenêtre principale
        primaryStage.setTitle("Application Affaires");

        // HEADER
        HBox header = new HBox(10);
        header.setStyle("-fx-padding: 10;");
        Text logo = new Text("Logo");
        TextField searchBar = new TextField();
        HBox.setHgrow(searchBar, Priority.ALWAYS);
        Button profilBtn = new Button("Profil");
        Button notificationsBtn = new Button("Notifications");
        Button settingsBtn = new Button("Paramètres");

        header.getChildren().addAll(logo, searchBar, profilBtn, notificationsBtn, settingsBtn);

        // MENU LATÉRAL
        VBox sideMenu = new VBox(10);
        Button affairesEnCoursBtn = new Button("Affaires en cours");
        Button rechercheAvanceeBtn = new Button("Recherche avancée");
        Button analyseLiensBtn = new Button("Analyse de liens");
        Button predictionsBtn = new Button("Prédictions de suspects");
        Button historiqueBtn = new Button("Historique des commentaires");
        Button settingsMenuBtn = new Button("Paramètres");

        sideMenu.getChildren().addAll(affairesEnCoursBtn, rechercheAvanceeBtn, analyseLiensBtn, predictionsBtn, historiqueBtn, settingsMenuBtn);
        sideMenu.setStyle("-fx-padding: 20;");

        // SECTION PRINCIPALE
        VBox mainSection = new VBox(20);
        Text affairesRecientesTitle = new Text("Affaires récentes");
        HBox affairesRecientes = new HBox(20);
        affairesRecientes.getChildren().addAll(createAffaireCard(), createAffaireCard(), createAffaireCard());

        Text affairesArchiveesTitle = new Text("Affaires archivées");
        HBox affairesArchivees = new HBox(20);
        affairesArchivees.getChildren().addAll(createAffaireCard(), createAffaireCard(), createAffaireCard());

        mainSection.getChildren().addAll(affairesRecientesTitle, affairesRecientes, affairesArchiveesTitle, affairesArchivees);

        // Layout Principal
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setLeft(sideMenu);
        root.setCenter(mainSection);

        // Scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour créer une carte d'affaire
    private VBox createAffaireCard() {
        VBox card = new VBox(10);
        card.setStyle("-fx-border-color: gray; -fx-padding: 10;");
        Text image = new Text("Image");
        Text titre = new Text("Titre");
        Text statut = new Text("Statut");
        Text description = new Text("Description");

        card.getChildren().addAll(image, titre, statut, description);
        return card;
    }

    // Méthode main pour lancer l'application
    public static void main(String[] args) {
        launch(args);  // Lance l'application JavaFX
    }
}
