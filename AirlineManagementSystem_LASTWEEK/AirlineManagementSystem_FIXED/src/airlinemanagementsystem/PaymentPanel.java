package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

class PaymentPanel extends JPanel {
    PaymentPanel(DashboardFrame dashboard) {
        setBackground(T.BG);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900, 520));

        CardPanel card = new CardPanel(null);
        card.setPreferredSize(new Dimension(500, 380));

        JLabel h = new JLabel("💳  Payment");
        h.setFont(T.F_TITLE);
        h.setForeground(T.TEXT);
        h.setBounds(24, 16, 412, 30);
        card.add(h);

        new JLabel("BOOKING ID") {{ setFont(T.F_LBL); setForeground(T.LBL); setBounds(24, 60, 412, 16); card.add(this); }};
        PField idField = new PField("Enter booking ID (e.g. BK...)");
        idField.setBounds(24, 78, 412, 40);
        card.add(idField);

        new JLabel("PAYMENT METHOD") {{ setFont(T.F_LBL); setForeground(T.LBL); setBounds(24, 132, 412, 16); card.add(this); }};
        JComboBox<String> methodBox = new JComboBox<>(new String[]{"Cash", "Credit/Debit Card", "bKash", "Nagad", "Rocket"});
        ComboBoxUtils.style(methodBox);
        methodBox.setBounds(24, 150, 412, 40);
        card.add(methodBox);

        GoldBtn pay = new GoldBtn("✔  Pay Now");
        pay.setBounds(24, 240, 180, 42);
        card.add(pay);

        pay.addActionListener(e -> {
            String bookingId = idField.getText().trim();
            if (bookingId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Booking ID!");
                return;
            }

            for (Reservation r : AirlineManagementSystem.data.reservations) {
                if (r.id.equals(bookingId)) {
                    if (r.paid) {
                        JOptionPane.showMessageDialog(this, "This booking has already been paid!");
                        return;
                    }
                    r.paid = true;
                    r.paymentMethod = (String) methodBox.getSelectedItem();
                    AirlineManagementSystem.saveData();
                    dashboard.refreshAllPanels();
                    JOptionPane.showMessageDialog(this,
                            "✔ Payment Successful!\n" +
                            "Booking ID : " + r.id + "\n" +
                            "Passenger  : " + r.name + "\n" +
                            "Method     : " + r.paymentMethod);
                    idField.setText("");
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Booking ID not found! Check the Reservations tab.");
        });

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(T.BG);
        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);
    }
}
