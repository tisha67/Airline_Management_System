package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

class ReportPanel extends JPanel {
    private JPanel grid;
    private JTextArea log;

    ReportPanel() {
        setBackground(T.BG);
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 28, 20, 28));

        JLabel h = new JLabel("📊  Daily Report");
        h.setFont(T.F_TITLE);
        h.setForeground(T.TEXT);
        add(h, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(2, 4, 16, 16));
        grid.setBackground(T.BG);
        add(grid, BorderLayout.CENTER);

        log = new JTextArea();
        log.setEditable(false);
        log.setFont(T.F_MONO);
        log.setBackground(new Color(8, 14, 40));
        log.setForeground(T.MUTED);
        log.setMargin(new Insets(14, 16, 14, 16));
        JScrollPane logScroll = new JScrollPane(log);
        logScroll.setBorder(new RoundBorder(T.F_BORDER, 10));
        logScroll.setPreferredSize(new Dimension(0, 160));
        add(logScroll, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        grid.removeAll();
        AppData data = AirlineManagementSystem.data;

        int totalSeats = 0;
        int bookedSeats = 0;
        double totalOccupancy = 0;
        for (Flight flight : data.flights) {
            totalSeats += flight.totalSeats;
            bookedSeats += flight.getBookedSeats();
            totalOccupancy += flight.getOccupancyPercentage();
        }

        double avgOccupancy = data.flights.isEmpty() ? 0 : totalOccupancy / data.flights.size();
        long openTickets = data.supportTickets.stream().filter(ticket -> "OPEN".equals(ticket.status)).count();
        long paidBookings = data.reservations.stream().filter(reservation -> reservation.paid).count();
        double revenue = data.reservations.stream()
                .filter(reservation -> reservation.paid)
                .mapToDouble(this::findFlightPrice)
                .sum();

        addStat(grid, "Total Flights", String.valueOf(data.flights.size()), T.CYAN);
        addStat(grid, "Total Seats", String.valueOf(totalSeats), T.TEXT);
        addStat(grid, "Booked Seats", String.valueOf(bookedSeats), T.GOLD);
        addStat(grid, "Avg Occupancy", String.format("%.1f%%", avgOccupancy), T.SUCCESS);
        addStat(grid, "Total Bookings", String.valueOf(data.reservations.size()), T.CYAN);
        addStat(grid, "Revenue (BDT)", String.format("%.0f", revenue), T.GOLD);
        addStat(grid, "Staff Members", String.valueOf(data.staffMembers.size()), T.TEXT);
        addStat(grid, "Open Tickets", String.valueOf(openTickets), openTickets > 0 ? T.DANGER : T.SUCCESS);

        grid.revalidate();
        grid.repaint();

        String sep = "═".repeat(52);
        log.setText(sep + "\n  ✈  AIRLINE MANAGEMENT SYSTEM— DAILY REPORT  |  " + LocalDate.now() + "\n" + sep + "\n\n" +
                "  Flights        : " + data.flights.size() + "  |  Total Seats: " + totalSeats + "  |  Booked: " + bookedSeats + "\n" +
                "  Avg Occupancy  : " + String.format("%.1f%%", avgOccupancy) + "\n" +
                "  Reservations   : " + data.reservations.size() + "  |  Paid: " + paidBookings + "  |  Unpaid: " + (data.reservations.size() - paidBookings) + "\n" +
                "  Revenue        : BDT " + String.format("%.0f", revenue) + "\n" +
                "  Staff Members  : " + data.staffMembers.size() + "\n" +
                "  Support Tickets: " + data.supportTickets.size() + "  |  Open: " + openTickets + "\n\n" + sep);
    }

    private double findFlightPrice(Reservation reservation) {
        for (Flight flight : AirlineManagementSystem.data.flights) {
            boolean sameFlightId = reservation.flightId != null && !reservation.flightId.isEmpty() && flight.id.equals(reservation.flightId);
            boolean sameFlightName = flight.name.equals(reservation.flight);
            if (sameFlightId || sameFlightName) {
                return flight.price;
            }
        }
        return 0;
    }

    private void addStat(JPanel panel, String label, String value, Color color) {
        CardPanel card = new CardPanel(new BorderLayout(0, 6));
        card.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);

        JLabel labelText = new JLabel(label, SwingConstants.CENTER);
        labelText.setFont(new Font("Segoe UI", Font.BOLD, 11));
        labelText.setForeground(T.MUTED);

        card.add(valueLabel, BorderLayout.CENTER);
        card.add(labelText, BorderLayout.SOUTH);
        panel.add(card);
    }
}
