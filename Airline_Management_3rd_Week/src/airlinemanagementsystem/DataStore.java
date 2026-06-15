package airlinemanagementsystem;

import java.io.*;

public class DataStore {
    private static final String DATA_FILE = "week3_airline_data.ser";

    public static void save(AppData data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(data);
        } catch (IOException e) {
            JOptionPaneHelper.error(null, "Data save failed: " + e.getMessage());
        }
    }

    public static AppData load() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return new AppData();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (AppData) in.readObject();
        } catch (Exception e) {
            return new AppData();
        }
    }
}
