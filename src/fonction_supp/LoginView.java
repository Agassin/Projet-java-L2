package src.fonction_supp;

import src.vue.AffaireView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginView extends JFrame {

    public LoginView(AffaireView mainView) {   // Constructor
        Map<String, String> users = new HashMap<>();
        users.put("admin", "password123");

        setTitle("Connexion");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Identifiant:"));
        JTextField usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Mot de passe:"));
        JPasswordField passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Se connecter");
        /*
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifierConnexion();
            }
        });
        */
        add(loginButton);
    }


/*
    private void verifierConnexion() {  // Method
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (users.containsKey(username) && users.get(username).equals(password)) {
            JOptionPane.showMessageDialog(this, "Connexion réussie !");
            dispose();
            mainView.afficherProfil(username);
        } else {
            JOptionPane.showMessageDialog(this, "Identifiant ou mot de passe incorrect !");
        }
    }
*/
}


