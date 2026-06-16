package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

class CancelPanel extends JPanel {
    CancelPanel(DashboardFrame dashboard) {
        setBackground(T.BG);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900, 460));

        CardPanel card = new CardPanel(null);
        card.setPreferredSize(new Dimension(460, 260));

        JLabel h = new JLabel("❌  Cancel Ticket");
        h.setFont(T.F_TITLE);
        h.setForeground(T.TEXT);
        h.setBounds(24, 16, 392, 30);
        card.add(h);

        new JLabel("BOOKING ID") {{ setFont(T.F_LBL); setForeground(T.LBL); setBounds(24, 60, 392, 16); card.add(this); }};
        PField idField = new PField("Enter booking ID (e.g. BK...)");
        idField.setBounds(24, 78, 392, 40);
        card.add(idField);

        JButton cancelBtn = new JButton("❌  Cancel Booking");
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cancelBtn.setForeground(T.TEXT);
        cancelBtn.setBackground(T.DANGER);
        cancelBtn.setOpaque(true);
        cancelBtn.setContentAreaFilled(true);
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelBtn.setBounds(24, 140, 200, 42);
        card.add(cancelBtn);
        cancelBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Cancel Booking clicked"));

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(T.BG);
        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);
    }
}

