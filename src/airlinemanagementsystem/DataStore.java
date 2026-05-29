package airlinemanagementsystem;

import java.io.*;
import java.util.ArrayList;

public class DataStore {
    private static final String USER_FILE = "users.dat";
    private static final String PASSENGER_FILE = "passengers.dat";

    public static void saveUsers(ArrayList<User> users) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.out.println("User save error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<User> loadUsers() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            return (ArrayList<User>) in.readObject();
        } catch (Exception e) {
            System.out.println("User load error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void savePassengers(ArrayList<Passenger> passengers) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PASSENGER_FILE))) {
            out.writeObject(passengers);
        } catch (IOException e) {
            System.out.println("Passenger save error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Passenger> loadPassengers() {
        File file = new File(PASSENGER_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(PASSENGER_FILE))) {
            return (ArrayList<Passenger>) in.readObject();
        } catch (Exception e) {
            System.out.println("Passenger load error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
