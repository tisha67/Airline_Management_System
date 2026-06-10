package airlinemanagementsystem;

import javax.swing.*;

public class JOptionPaneHelper {
    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
