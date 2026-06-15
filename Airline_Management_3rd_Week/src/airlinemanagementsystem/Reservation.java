package airlinemanagementsystem;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 2L;
    private String bookingId;
    private String passengerName;
    private String email;
    private String phone;
    private String flightId;
    private String flightName;
    private String seatNo;
    private boolean paid;
    private String paymentMethod;
    private LocalDateTime bookingDate;

    public Reservation(String passengerName, String email, String phone, String flightId, String flightName) {
        this.bookingId = "BK" + System.currentTimeMillis();
        this.passengerName = passengerName;
        this.email = email;
        this.phone = phone;
        this.flightId = flightId;
        this.flightName = flightName;
        this.seatNo = generateSeatNo();
        this.paid = false;
        this.paymentMethod = "Not Paid";
        this.bookingDate = LocalDateTime.now();
    }

    private String generateSeatNo() {
        String[] letters = {"A", "B", "C", "D", "E", "F"};
        int row = (int)(Math.random() * 30) + 1;
        String col = letters[(int)(Math.random() * letters.length)];
        return col + row;
    }

    public String getBookingId() { return bookingId; }
    public String getPassengerName() { return passengerName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getFlightId() { return flightId; }
    public String getFlightName() { return flightName; }
    public String getSeatNo() { return seatNo; }
    public boolean isPaid() { return paid; }
    public String getPaymentMethod() { return paymentMethod; }
    public LocalDateTime getBookingDate() { return bookingDate; }

    public void makePayment(String method) {
        this.paid = true;
        this.paymentMethod = method;
    }
}
