package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ReservationPanel extends JPanel {
    private DefaultTableModel tableModel;

    public ReservationPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("All Reservations");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Theme.GOLD);
        add(title, BorderLayout.NORTH);

        String[] cols = {"SL", "Booking ID", "Passenger", "Flight", "Seat", "Phone", "Status", "Payment", "Date"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(tableModel);
        Theme.styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Theme.BG);
        add(scrollPane, BorderLayout.CENTER);
        refreshTable();
    }

    public void refreshTable() {
        if (tableModel == null) return;
        tableModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int sl = 1;
        for (Reservation r : AirlineManagementSystem.data.reservations) {
            tableModel.addRow(new Object[]{
                    sl++, r.getBookingId(), r.getPassengerName(), r.getFlightName(), r.getSeatNo(),
                    r.getPhone(), r.isPaid() ? "PAID" : "UNPAID", r.getPaymentMethod(), r.getBookingDate().format(fmt)
            });
        }
    }
}
