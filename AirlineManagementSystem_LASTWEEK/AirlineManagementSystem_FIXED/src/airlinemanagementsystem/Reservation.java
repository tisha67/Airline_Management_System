package airlinemanagementsystem;

import java.io.Serializable;
import java.time.LocalDateTime;

class Reservation implements Serializable {
    private static final long serialVersionUID = 2L;

    String name;
    String email;
    String phone;
    String flight;
    String flightId;
    String id;
    String paymentMethod;
    String seatNo;
    boolean paid;
    boolean checkedIn;
    LocalDateTime bookingDate;

    Reservation(String name, String email, String phone, String flightName, String flightId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.flight = flightName;
        this.flightId = flightId;
        this.id = "BK" + System.currentTimeMillis();
        this.bookingDate = LocalDateTime.now();
        this.paid = false;
        this.checkedIn = false;
        this.seatNo = generateSeat();
    }

    // Backward-compatible constructor for old saved data or simple usage.
    Reservation(String name, String email, String phone, String flightName) {
        this(name, email, phone, flightName, "");
    }

    private String generateSeat() {
        String[] rows = {"A", "B", "C", "D", "E", "F"};
        return rows[(int) (Math.random() * rows.length)] + (int) (Math.random() * 30 + 1);
    }

    public String getStatusBadge() {
        if (checkedIn) return "✓ CHECKED-IN";
        if (paid) return "✓ PAID";
        return "⏳ UNPAID";
    }
}
