package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

public class BookPanel extends JPanel {
    private DashboardFrame dashboard;
    private JTextField nameField, emailField, phoneField;
    private JComboBox<Flight> flightBox;
    private JLabel flightInfoLabel;

    public BookPanel(DashboardFrame dashboard) {
        this.dashboard = dashboard;
        setLayout(new GridBagLayout());
        setBackground(Theme.BG);

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(480, 420));
        card.setBackground(Theme.CARD);

        JLabel title = new JLabel("Book a Ticket");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Theme.GOLD);
        title.setBounds(30, 20, 420, 35);
        card.add(title);

        nameField = addField(card, "Passenger Name", 75);
        emailField = addField(card, "Email Address", 135);
        phoneField = addField(card, "Phone Number", 195);

        JLabel flightLabel = new JLabel("Select Flight");
        flightLabel.setForeground(Theme.TEXT);
        flightLabel.setBounds(30, 255, 420, 20);
        card.add(flightLabel);

        flightBox = new JComboBox<>();
        flightBox.setBounds(30, 280, 420, 32);
        flightBox.addActionListener(e -> updateFlightInfo());
        card.add(flightBox);

        flightInfoLabel = new JLabel("Add flights from Flight Management first.");
        flightInfoLabel.setForeground(Theme.MUTED);
        flightInfoLabel.setBounds(30, 318, 420, 22);
        card.add(flightInfoLabel);

        JButton bookButton = new JButton("Confirm Booking");
        Theme.styleButton(bookButton);
        bookButton.setBounds(30, 355, 190, 38);
        card.add(bookButton);
        bookButton.addActionListener(e -> bookTicket());

        add(card);
        refreshFlights();
    }

    private JTextField addField(JPanel panel, String label, int y) {
        JLabel l = new JLabel(label);
        l.setForeground(Theme.TEXT);
        l.setBounds(30, y, 420, 18);
        panel.add(l);
        JTextField field = new JTextField();
        field.setBounds(30, y + 22, 420, 30);
        panel.add(field);
        return field;
    }

    public void refreshFlights() {
        if (flightBox == null) return;
        Flight selected = (Flight) flightBox.getSelectedItem();
        flightBox.removeAllItems();
        for (Flight flight : AirlineManagementSystem.data.flights) {
            if (flight.hasSeat()) flightBox.addItem(flight);
        }
        if (selected != null) {
            for (int i = 0; i < flightBox.getItemCount(); i++) {
                if (flightBox.getItemAt(i).getId().equals(selected.getId())) {
                    flightBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        updateFlightInfo();
    }

    private void updateFlightInfo() {
        Flight flight = (Flight) flightBox.getSelectedItem();
        if (flight == null) {
            flightInfoLabel.setText("No available flight found.");
        } else {
            flightInfoLabel.setText("Available: " + flight.getAvailableSeats() + " seats | Price: BDT " + (int)flight.getPrice());
        }
    }

    private void bookTicket() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        Flight selected = (Flight) flightBox.getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPaneHelper.warning(this, "All passenger fields are required.");
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPaneHelper.warning(this, "Invalid email format.");
            return;
        }
        if (!phone.matches("\\d{10,}")) {
            JOptionPaneHelper.warning(this, "Phone number must contain at least 10 digits.");
            return;
        }
        if (selected == null) {
            JOptionPaneHelper.warning(this, "Please select an available flight.");
            return;
        }
        if (!selected.bookOneSeat()) {
            JOptionPaneHelper.warning(this, "No seat available for this flight.");
            return;
        }

        Reservation reservation = new Reservation(name, email, phone, selected.getId(), selected.getName());
        AirlineManagementSystem.data.reservations.add(reservation);
        AirlineManagementSystem.saveData();

        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        dashboard.refreshAll();

        JOptionPaneHelper.info(this,
                "Booking Confirmed!\n\n" +
                "Booking ID: " + reservation.getBookingId() + "\n" +
                "Passenger: " + reservation.getPassengerName() + "\n" +
                "Flight: " + reservation.getFlightName() + "\n" +
                "Seat No: " + reservation.getSeatNo() + "\n" +
                "Price: BDT " + (int)selected.getPrice() + "\n\n" +
                "Please complete payment from Payment tab.");
    }
}
