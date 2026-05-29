package airlinemanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class AirlineManagementSystem {
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<User> users = DataStore.loadUsers();
    private static ArrayList<Passenger> passengers = DataStore.loadPassengers();
    private static ArrayList<Flight> flights = new ArrayList<>();

    public static void main(String[] args) {
        addSampleFlights();
        System.out.println("================================");
        System.out.println("  Airlines Management System");
        System.out.println("================================");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice = readInt();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    User loggedInUser = loginUser();
                    if (loggedInUser != null) {
                        showMainMenu(loggedInUser);
                    }
                    break;
                case 3:
                    DataStore.saveUsers(users);
                    DataStore.savePassengers(passengers);
                    System.out.println("Data saved. Thank you!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.println("\n--- Register New User ---");
        System.out.print("Full name: ");
        String name = input.nextLine();
        System.out.print("Username : ");
        String username = input.nextLine();
        System.out.print("Password : ");
        String password = input.nextLine();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }

        users.add(new User(name, username, password));
        DataStore.saveUsers(users);
        System.out.println("Registration successful. Please login now.");
    }

    private static User loginUser() {
        System.out.println("\n--- User Login ---");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful. Welcome, " + user.getName() + "!");
                return user;
            }
        }

        System.out.println("Invalid username or password.");
        return null;
    }

    private static void showMainMenu(User user) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("Logged in as: " + user.getName());
            System.out.println("1. View Flights");
            System.out.println("2. Add Passenger Booking");
            System.out.println("3. View Passenger Bookings");
            System.out.println("4. Logout");
            System.out.print("Choose option: ");
            int choice = readInt();

            switch (choice) {
                case 1:
                    viewFlights();
                    break;
                case 2:
                    addPassengerBooking();
                    break;
                case 3:
                    viewPassengers();
                    break;
                case 4:
                    DataStore.savePassengers(passengers);
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addSampleFlights() {
        flights.add(new Flight("SW-101", "Dhaka", "Chittagong", "09:30", 120, 3500));
        flights.add(new Flight("SW-202", "Dhaka", "Cox's Bazar", "12:00", 100, 4500));
        flights.add(new Flight("SW-303", "Chittagong", "Sylhet", "16:15", 90, 4000));
    }

    private static void viewFlights() {
        System.out.println("\n--- Available Flights ---");
        for (int i = 0; i < flights.size(); i++) {
            System.out.println("Flight No: " + (i + 1));
            flights.get(i).displayFlight();
        }
    }

    private static void addPassengerBooking() {
        System.out.println("\n--- Add Passenger Booking ---");
        viewFlights();
        System.out.print("Select flight number: ");
        int flightNo = readInt();

        if (flightNo < 1 || flightNo > flights.size()) {
            System.out.println("Invalid flight number.");
            return;
        }

        System.out.print("Passenger name : ");
        String name = input.nextLine();
        System.out.print("Phone number   : ");
        String phone = input.nextLine();

        if (name.isEmpty() || phone.isEmpty()) {
            System.out.println("Passenger name and phone are required.");
            return;
        }

        Flight selectedFlight = flights.get(flightNo - 1);
        passengers.add(new Passenger(name, phone, selectedFlight.getFlightName()));
        DataStore.savePassengers(passengers);
        System.out.println("Passenger booking added successfully.");
    }

    private static void viewPassengers() {
        System.out.println("\n--- Passenger Booking List ---");
        if (passengers.isEmpty()) {
            System.out.println("No passenger bookings found.");
            return;
        }

        for (Passenger passenger : passengers) {
            passenger.displayPassenger();
        }
    }

    private static int readInt() {
        try {
            int value = Integer.parseInt(input.nextLine());
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
