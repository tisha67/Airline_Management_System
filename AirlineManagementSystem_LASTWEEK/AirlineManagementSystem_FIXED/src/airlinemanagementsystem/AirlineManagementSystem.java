package airlinemanagementsystem;

import javax.swing.*;

public class AirlineManagementSystem {
    static AppData data = DataStore.load();

    public static void main(String[] args) {
        if (!data.users.isEmpty()) {
            data.users.get(0).setAdmin(true);
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(LoginFrame::new);
    }

    public static void saveData() {
        DataStore.save(data);
    }
}
