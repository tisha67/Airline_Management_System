package airlinemanagementsystem;

import javax.swing.*;

public class AirlineManagementSystem {
    static AppData data = DataStore.load();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginFrame());
    }

    public static void saveData() {
        DataStore.save(data);
    }
}
