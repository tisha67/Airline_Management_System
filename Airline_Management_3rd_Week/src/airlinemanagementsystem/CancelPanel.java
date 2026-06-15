package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

public class CancelPanel extends JPanel {
    private DashboardFrame dashboard;
    private JTextField bookingIdField;

    public CancelPanel(DashboardFrame dashboard) {
        this.dashboard = dashboard;
        setLayout(new GridBagLayout());
        setBackground(Theme.BG);

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(430, 230));
        card.setBackground(Theme.CARD);

        JLabel title = new JLabel("Cancel Ticket");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Theme.GOLD);
        title.setBounds(30, 25, 370, 35);
        card.add(title);

        JLabel idLabel = new JLabel("Booking ID");
        idLabel.setForeground(Theme.TEXT);
        idLabel.setBounds(30, 85, 370, 20);
        card.add(idLabel);

        bookingIdField = new JTextField();
        bookingIdField.setBounds(30, 110, 370, 32);
        card.add(bookingIdField);

        JButton cancelButton = new JButton("Cancel Booking");
        Theme.styleDangerButton(cancelButton);
        cancelButton.setBounds(30, 165, 170, 36);
        card.add(cancelButton);
        cancelButton.addActionListener(e -> cancelBooking());

        add(card);
    }

    private void cancelBooking() {
        String bookingId = bookingIdField.getText().trim();
        if (bookingId.isEmpty()) {
            JOptionPaneHelper.warning(this, "Please enter booking ID.");
            return;
        }
        for (Reservation r : AirlineManagementSystem.data.reservations) {
            if (r.getBookingId().equalsIgnoreCase(bookingId)) {
                int option = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to cancel booking for " + r.getPassengerName() + "?",
                        "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
                if (option != JOptionPane.YES_OPTION) return;

                for (Flight f : AirlineManagementSystem.data.flights) {
                    if (f.getId().equals(r.getFlightId())) {
                        f.restoreOneSeat();
                        break;
                    }
                }
                AirlineManagementSystem.data.reservations.remove(r);
                AirlineManagementSystem.saveData();
                bookingIdField.setText("");
                dashboard.refreshAll();
                JOptionPaneHelper.info(this, "Booking cancelled successfully. Seat has been restored.");
                return;
            }
        }
        JOptionPaneHelper.error(this, "Booking ID not found.");
    }
}
