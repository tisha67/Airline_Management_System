package airlinemanagementsystem;

import javax.swing.*;

public class AirlineManagementSystem {
    static AppData data = DataStore.load();

    public static void main(String[] args) {
        if (data.users.isEmpty()) {
            User admin = new User("Admin", "admin@skywave.com", "01700000000", "admin", "123456");
            admin.setAdmin(true);
            data.users.add(admin);
            DataStore.save(data);
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
