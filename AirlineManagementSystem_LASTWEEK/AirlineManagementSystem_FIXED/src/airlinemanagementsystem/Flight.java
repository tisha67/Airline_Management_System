package airlinemanagementsystem;

import java.io.Serializable;
import java.time.LocalDateTime;

class Flight implements Serializable {
    private static final long serialVersionUID = 2L;

    String id;
    String name;
    String source;
    String dest;
    String time;
    String status;
    String aircraft;
    String cabinClass;
    int seats;
    int totalSeats;
    double price;
    LocalDateTime createdDate;

    Flight(String name, String source, String dest, int seats, String time, double price) {
        this(name, source, dest, seats, time, price, "Economy");
    }

    Flight(String name, String source, String dest, int seats, String time, double price, String cabinClass) {
        this.id = "FL" + System.currentTimeMillis();
        this.name = name;
        this.source = source;
        this.dest = dest;
        this.seats = seats;
        this.totalSeats = seats;
        this.time = time;
        this.price = price;
        this.status = "ACTIVE";
        this.aircraft = "Boeing 737";
        this.cabinClass = (cabinClass == null || cabinClass.isBlank()) ? "Economy" : cabinClass;
        this.createdDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("%-12s | %s → %s | %d/%d seats | %s | %s | BDT %.0f | [%s]",
                name, source, dest, seats, totalSeats, time, cabinClass, price, status);
    }

    public int getBookedSeats() {
        return totalSeats - seats;
    }

    public double getOccupancyPercentage() {
        return totalSeats == 0 ? 0 : (getBookedSeats() * 100.0) / totalSeats;
    }

    public boolean isAvailable() {
        return "ACTIVE".equals(status) && seats > 0;
    }
}
