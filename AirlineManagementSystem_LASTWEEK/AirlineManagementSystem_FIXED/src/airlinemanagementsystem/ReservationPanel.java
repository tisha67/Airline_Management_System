package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

class ReservationPanel extends JPanel {
    private DefaultTableModel model;

    ReservationPanel() {
        setBackground(T.BG);
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 28, 20, 28));
        setPreferredSize(new Dimension(1000, 620));

        JLabel h = new JLabel("📋  All Reservations");
        h.setFont(T.F_TITLE);
        h.setForeground(T.TEXT);
        add(h, BorderLayout.NORTH);

        String[] cols = {"#", "Booking ID", "Passenger", "Flight", "Seat", "Phone", "Status", "Date"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        styleTable(table);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(55);
        table.getColumnModel().getColumn(6).setPreferredWidth(110);

        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(new Color(12, 20, 58));
        sp.setBorder(null);
        add(sp, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        model.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        java.util.List<Reservation> list = AirlineManagementSystem.data.reservations;

        for (int i = 0; i < list.size(); i++) {
            Reservation r = list.get(i);
            String date = r.bookingDate == null ? "-" : r.bookingDate.format(fmt);
            model.addRow(new Object[]{
                    i + 1,
                    r.id,
                    r.name,
                    r.flight,
                    r.seatNo != null ? r.seatNo : "-",
                    r.phone,
                    r.getStatusBadge(),
                    date
            });
        }
    }

    static void styleTable(JTable table) {
        table.setFont(T.F_BODY);
        table.setForeground(T.TEXT);
        table.setBackground(new Color(12, 20, 58));
        table.setGridColor(new Color(30, 50, 110));
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(40, 80, 180));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(8, 14, 42));
        table.getTableHeader().setForeground(T.GOLD);
    }
}
