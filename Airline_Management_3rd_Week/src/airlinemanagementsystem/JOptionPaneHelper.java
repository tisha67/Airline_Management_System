package airlinemanagementsystem;

import java.awt.Component;
import javax.swing.JOptionPane;

public class JOptionPaneHelper {
    public static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Airline Management System", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void warning(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Airline Management System", JOptionPane.WARNING_MESSAGE);
    }
    public static void error(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Airline Management System", JOptionPane.ERROR_MESSAGE);
    }
}
