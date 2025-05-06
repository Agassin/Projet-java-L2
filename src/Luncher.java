package src;

import src.fonction_supp.LoginView;
import javax.swing.*;

public class Luncher {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
