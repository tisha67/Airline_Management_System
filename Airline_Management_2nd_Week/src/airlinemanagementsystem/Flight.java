package airlinemanagementsystem;

import java.io.Serializable;

public class Flight implements Serializable {
    private static final long serialVersionUID = 1L;

    private String flightNo;
    private String source;
    private String destination;
    private String time;
    private int totalSeats;
    private int availableSeats;
    private double price;

    public Flight(String flightNo, String source, String destination, String time, int totalSeats, double price) {
        this.flightNo = flightNo;
        this.source = source;
        this.destination = destination;
        this.time = time;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.price = price;
    }

    public String getFlightNo() { return flightNo; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getTime() { return time; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public double getPrice() { return price; }

    public String toString() {
        return flightNo + " | " + source + " to " + destination + " | " + time + " | Seats: " + availableSeats + "/" + totalSeats + " | BDT " + price;
    }
}
