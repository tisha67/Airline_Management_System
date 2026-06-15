package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

public class PaymentPanel extends JPanel {
    private DashboardFrame dashboard;
    private JTextField bookingIdField;
    private JComboBox<String> methodBox;

    public PaymentPanel(DashboardFrame dashboard) {
        this.dashboard = dashboard;
        setLayout(new GridBagLayout());
        setBackground(Theme.BG);

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(440, 290));
        card.setBackground(Theme.CARD);

        JLabel title = new JLabel("Payment Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Theme.GOLD);
        title.setBounds(30, 25, 380, 35);
        card.add(title);

        JLabel idLabel = new JLabel("Booking ID");
        idLabel.setForeground(Theme.TEXT);
        idLabel.setBounds(30, 85, 380, 20);
        card.add(idLabel);

        bookingIdField = new JTextField();
        bookingIdField.setBounds(30, 110, 380, 32);
        card.add(bookingIdField);

        JLabel methodLabel = new JLabel("Payment Method");
        methodLabel.setForeground(Theme.TEXT);
        methodLabel.setBounds(30, 160, 380, 20);
        card.add(methodLabel);

        methodBox = new JComboBox<>(new String[]{"Cash", "Credit/Debit Card", "bKash", "Nagad", "Rocket"});
        methodBox.setBounds(30, 185, 380, 32);
        card.add(methodBox);

        JButton payButton = new JButton("Pay Now");
        Theme.styleButton(payButton);
        payButton.setBounds(30, 235, 150, 36);
        card.add(payButton);
        payButton.addActionListener(e -> makePayment());

        add(card);
    }

    private void makePayment() {
        String bookingId = bookingIdField.getText().trim();
        if (bookingId.isEmpty()) {
            JOptionPaneHelper.warning(this, "Please enter booking ID.");
            return;
        }
        for (Reservation r : AirlineManagementSystem.data.reservations) {
            if (r.getBookingId().equalsIgnoreCase(bookingId)) {
                if (r.isPaid()) {
                    JOptionPaneHelper.warning(this, "This booking is already paid.");
                    return;
                }
                r.makePayment((String) methodBox.getSelectedItem());
                AirlineManagementSystem.saveData();
                bookingIdField.setText("");
                dashboard.refreshAll();
                JOptionPaneHelper.info(this,
                        "Payment Successful!\n\n" +
                        "Booking ID: " + r.getBookingId() + "\n" +
                        "Passenger: " + r.getPassengerName() + "\n" +
                        "Method: " + r.getPaymentMethod());
                return;
            }
        }
        JOptionPaneHelper.error(this, "Booking ID not found.");
    }
}
