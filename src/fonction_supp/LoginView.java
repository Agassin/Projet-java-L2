package src.fonction_supp;

import src.controller.AffaireController;
import src.model.Affaire;
import src.vue.AffaireView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Map<String, String> users;

    public LoginView() {
        users = new HashMap<>();
        users.put("admin", "caca"); // Configuration du compte admin

        setTitle("Connexion - Gendarmerie Nationale");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Connexion au système", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, gbc);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 10));
        formPanel.add(new JLabel("Identifiant:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        mainPanel.add(formPanel, gbc);

        JButton loginButton = new JButton("Se connecter");
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this::handleLogin);

        mainPanel.add(loginButton, gbc);
        add(mainPanel);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs",
                    "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("admin".equals(username) && "caca".equals(password)) {
            JOptionPane.showMessageDialog(this, "Connexion réussie!",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            openMainApplication();
        } else {
            JOptionPane.showMessageDialog(this, "Identifiants incorrects",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openMainApplication() {
        SwingUtilities.invokeLater(() -> {
            AffaireController controller = new AffaireController();
            // Ajout des affaires par défaut
            controller.ajouterAffaire(new Affaire("Affaire 1", "Vol", null, null, "Paris", "Ouvert", "2023-01-01"));
            controller.ajouterAffaire(new Affaire("Affaire 2", "Fraude", null, null, "Lyon", "Fermé", "2023-02-01"));
            controller.ajouterAffaire(new Affaire("Affaire 3", "Cambriolage", null, null, "Marseille", "En cours", "2023-03-01"));

            AffaireView mainView = new AffaireView(controller);
            mainView.setVisible(true);
        });
    }
}