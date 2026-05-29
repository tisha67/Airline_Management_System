package airlinemanagementsystem;

import java.io.Serializable;

public class Flight implements Serializable {
    private static final long serialVersionUID = 1L;

    private String flightName;
    private String source;
    private String destination;
    private String time;
    private int seats;
    private double price;

    public Flight(String flightName, String source, String destination, String time, int seats, double price) {
        this.flightName = flightName;
        this.source = source;
        this.destination = destination;
        this.time = time;
        this.seats = seats;
        this.price = price;
    }

    public String getFlightName() {
        return flightName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getTime() {
        return time;
    }

    public int getSeats() {
        return seats;
    }

    public double getPrice() {
        return price;
    }

    public void displayFlight() {
        System.out.println("Flight: " + flightName);
        System.out.println("Route : " + source + " to " + destination);
        System.out.println("Time  : " + time);
        System.out.println("Seats : " + seats);
        System.out.println("Price : BDT " + price);
        System.out.println("--------------------------------");
    }
}
