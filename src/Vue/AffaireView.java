package src.Vue;

import src.Controller.AffaireController;
import src.Model.Affaire;
import src.Model.Personne;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class AffaireView extends JFrame {
    private JPanel mainPanel;
    private JPanel dashboardPanel;
    private AffaireController controller;
    private JTextField searchField;
    private JPanel panelAffaires;

    public AffaireView(AffaireController controller) {
        this.controller = controller;
        setTitle("Gestion des Affaires - Tableau de bord");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(mainPanel);

        createHeader();
        createDashboard();

        setVisible(true);
    }

    private void createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(240, 240, 240));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel logoLabel = new JLabel("GENDARMERIE NATIONALE");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(logoLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(300, 30));
        searchPanel.add(searchField);
        header.add(searchPanel, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { rechercher(); }
            public void removeUpdate(DocumentEvent e) { rechercher(); }
            public void changedUpdate(DocumentEvent e) { rechercher(); }
        });
    }

    private void createDashboard() {
        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        panelAffaires = new JPanel(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panelAffaires);
        dashboardPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(dashboardPanel, BorderLayout.CENTER);

        afficherAffaires();
    }

    public void rechercher() {
        String searchText = searchField.getText();
        afficherAffaires(searchText);
    }

    public void afficherAffaires() {
        afficherAffaires("");
    }

    public void afficherAffaires(String searchText) {
        panelAffaires.removeAll();
        boolean found = false;

        for (Affaire affaire : controller.getAffaires()) {
            if (searchText.isEmpty() || affaire.getNomAffaire().toLowerCase().contains(searchText.toLowerCase())) {
                JPanel carte = new JPanel(new BorderLayout());
                carte.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                carte.add(new JLabel("Affaire : " + affaire.getNomAffaire()), BorderLayout.CENTER);
                panelAffaires.add(carte);
                found = true;
            }
        }

        for (Personne personne : controller.getPersonnes()) {
            if (searchText.isEmpty() || personne.getNomComplet().toLowerCase().contains(searchText.toLowerCase())) {
                JPanel cartePersonne = new JPanel(new BorderLayout());
                cartePersonne.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                cartePersonne.add(new JLabel("Personne : " + personne.getNomComplet()), BorderLayout.CENTER);
                panelAffaires.add(cartePersonne);
                found = true;
            }
        }

        if (!found) {
            panelAffaires.add(new JLabel("Aucune correspondance trouvée."));
        }

        panelAffaires.revalidate();
        panelAffaires.repaint();
    }
}
