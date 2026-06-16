package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

class BookPanel extends JPanel {
    private final DashboardFrame dashboard;
    private JComboBox<Flight> flightBox;
    private JLabel info;
    private JLabel lastIdLabel;
    private JButton copyBtn;
    private String lastBookingId = "";
    private final PField[] fields = new PField[3];

    BookPanel(DashboardFrame dashboard) {
        this.dashboard = dashboard;
        setBackground(T.BG);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900, 620));

        CardPanel card = new CardPanel(null);
        card.setPreferredSize(new Dimension(500, 500));

        JLabel h = new JLabel("🎫  Book a Ticket");
        h.setFont(T.F_TITLE);
        h.setForeground(T.TEXT);
        h.setBounds(24, 16, 412, 30);
        card.add(h);

        String[] labels = {"PASSENGER NAME", "EMAIL ADDRESS", "PHONE NUMBER"};
        String[] placeholders = {"Full name", "you@email.com", "01XXXXXXXXX"};
        int[] ys = {56, 122, 188};
        for (int i = 0; i < 3; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(T.F_LBL);
            label.setForeground(T.LBL);
            label.setBounds(24, ys[i], 412, 16);
            card.add(label);
            fields[i] = new PField(placeholders[i]);
            fields[i].setBounds(24, ys[i] + 18, 412, 40);
            card.add(fields[i]);
        }

        JLabel fl = new JLabel("SELECT FLIGHT");
        fl.setFont(T.F_LBL);
        fl.setForeground(T.LBL);
        fl.setBounds(24, 254, 412, 16);
        card.add(fl);

        flightBox = new JComboBox<>();
        ComboBoxUtils.style(flightBox);
        flightBox.setBounds(24, 272, 412, 40);
        card.add(flightBox);

        info = new JLabel("Add flights from the Flights tab first.");
        info.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        info.setForeground(T.SUCCESS);
        info.setBounds(24, 316, 412, 18);
        card.add(info);

        GoldBtn book = new GoldBtn("🎫  Confirm Booking");
        book.setBounds(24, 348, 220, 42);
        card.add(book);
        book.addActionListener(e -> bookTicket());

        lastIdLabel = new JLabel("Last Booking ID: -");
        lastIdLabel.setFont(T.F_BODY);
        lastIdLabel.setForeground(T.TEXT);
        lastIdLabel.setBounds(24, 398, 300, 20);
        card.add(lastIdLabel);

        copyBtn = new GoldBtn("Copy Booking ID");
        copyBtn.setBounds(24, 430, 170, 38);
        copyBtn.setEnabled(false);
        card.add(copyBtn);
        copyBtn.addActionListener(e -> {
            if (lastBookingId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No booking ID yet. Please book a ticket first.");
                return;
            }
            ComboBoxUtils.copyToClipboard(lastBookingId);
            JOptionPane.showMessageDialog(this, "Booking ID copied!");
        });

        flightBox.addActionListener(e -> updateFlightInfo());

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(T.BG);
        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);

        refresh();
    }

    private void updateFlightInfo() {
        Flight selected = (Flight) flightBox.getSelectedItem();
        if (selected != null) {
            info.setText("Available: " + selected.seats + " seats  |  Price: BDT " + (int) selected.price);
        } else {
            info.setText(AirlineManagementSystem.data.flights.isEmpty()
                    ? "No flights available. Add flights in the Flights tab."
                    : "Select a flight above.");
        }
    }

    private void bookTicket() {
        String name = fields[0].getText().trim();
        String email = fields[1].getText().trim();
        String phone = fields[2].getText().trim();
        Flight selected = (Flight) flightBox.getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields required!");
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email address!");
            return;
        }
        if (!phone.matches("\\d{10,}")) {
            JOptionPane.showMessageDialog(this, "Phone must be at least 10 digits!");
            return;
        }
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a flight! Add flights from the Flights tab.");
            return;
        }
        if (selected.seats <= 0) {
            JOptionPane.showMessageDialog(this, "No seats available on this flight!");
            return;
        }

        selected.seats--;
        Reservation reservation = new Reservation(name, email, phone, selected.name, selected.id);
        AirlineManagementSystem.data.reservations.add(reservation);
        AirlineManagementSystem.saveData();
        dashboard.refreshAllPanels();

        lastBookingId = reservation.id;
        lastIdLabel.setText("Last Booking ID: " + lastBookingId);
        copyBtn.setEnabled(true);
        ComboBoxUtils.copyToClipboard(lastBookingId);

        JOptionPane.showMessageDialog(this,
                "✓ Booking Confirmed!\n" +
                "Booking ID : " + reservation.id + "\n" +
                "Passenger  : " + reservation.name + "\n" +
                "Flight     : " + selected.name + "\n" +
                "Seat No.   : " + reservation.seatNo + "\n" +
                "Price      : BDT " + (int) selected.price + "\n\n" +
                "Booking ID copied to clipboard.");

        for (PField field : fields) field.setText("");
        updateFlightInfo();
    }

    public void refresh() {
        Flight previous = (Flight) flightBox.getSelectedItem();
        flightBox.removeAllItems();
        for (Flight flight : AirlineManagementSystem.data.flights) {
            if (flight.isAvailable()) {
                flightBox.addItem(flight);
            }
        }

        if (previous != null) {
            for (int i = 0; i < flightBox.getItemCount(); i++) {
                if (flightBox.getItemAt(i).id.equals(previous.id)) {
                    flightBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        updateFlightInfo();
    }
}
