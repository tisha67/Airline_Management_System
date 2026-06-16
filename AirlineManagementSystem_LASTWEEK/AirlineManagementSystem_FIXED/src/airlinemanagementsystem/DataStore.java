package airlinemanagementsystem;

import java.io.*;

class DataStore {
    static final String DATA_FILE = "airline_data.ser";

    public static void save(AppData data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(data);
        } catch (IOException e) {
            System.err.println("Save error: " + e.getMessage());
        }
    }

    public static AppData load() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return new AppData();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (AppData) in.readObject();
        } catch (Exception e) {
            System.err.println("Load error: " + e.getMessage());
            return new AppData();
        }
    }
}
