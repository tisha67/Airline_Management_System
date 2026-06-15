package airlinemanagementsystem;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Flight implements Serializable {
    private static final long serialVersionUID = 2L;
    private String id;
    private String name;
    private String source;
    private String destination;
    private String departureTime;
    private int availableSeats;
    private int totalSeats;
    private double price;
    private LocalDateTime createdAt;

    public Flight(String name, String source, String destination, int totalSeats, String departureTime, double price) {
        this.id = "FL" + System.currentTimeMillis();
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.departureTime = departureTime;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getDepartureTime() { return departureTime; }
    public int getAvailableSeats() { return availableSeats; }
    public int getTotalSeats() { return totalSeats; }
    public double getPrice() { return price; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public int getBookedSeats() { return totalSeats - availableSeats; }

    public boolean hasSeat() { return availableSeats > 0; }

    public boolean bookOneSeat() {
        if (availableSeats <= 0) return false;
        availableSeats--;
        return true;
    }

    public void restoreOneSeat() {
        if (availableSeats < totalSeats) availableSeats++;
    }

    @Override
    public String toString() {
        return name + " | " + source + " to " + destination + " | Seats: " + availableSeats + "/" + totalSeats + " | BDT " + (int)price;
    }
}
