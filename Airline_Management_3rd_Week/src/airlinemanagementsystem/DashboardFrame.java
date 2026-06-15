package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private User currentUser;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JLabel statsLabel;
    private FlightPanel flightPanel;
    private BookPanel bookPanel;
    private ReservationPanel reservationPanel;
    private PaymentPanel paymentPanel;
    private CancelPanel cancelPanel;

    public DashboardFrame(User user) {
        this.currentUser = user;
        setTitle(" Airline Management System");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.BG);
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildSideMenu(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Theme.BG);

        flightPanel = new FlightPanel(this);
        bookPanel = new BookPanel(this);
        reservationPanel = new ReservationPanel();
        paymentPanel = new PaymentPanel(this);
        cancelPanel = new CancelPanel(this);

        contentPanel.add(flightPanel, "Flights");
        contentPanel.add(bookPanel, "Book");
        contentPanel.add(reservationPanel, "Reservations");
        contentPanel.add(paymentPanel, "Payment");
        contentPanel.add(cancelPanel, "Cancel");

        root.add(contentPanel, BorderLayout.CENTER);
        refreshAll();
        setVisible(true);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(8, 14, 38));
        header.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel title = new JLabel("✈  Airline Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 19));
        title.setForeground(Theme.GOLD);
        header.add(title, BorderLayout.WEST);

        statsLabel = new JLabel();
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statsLabel.setForeground(Theme.TEXT);
        header.add(statsLabel, BorderLayout.CENTER);

        JButton logout = new JButton("Logout");
        Theme.styleDangerButton(logout);
        logout.addActionListener(e -> {
            AirlineManagementSystem.saveData();
            new LoginFrame();
            dispose();
        });
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        JLabel name = new JLabel("Welcome, " + currentUser.getName());
        name.setForeground(Theme.MUTED);
        right.add(name);
        right.add(logout);
        header.add(right, BorderLayout.EAST);

        return header;
    }

    private JPanel buildSideMenu() {
        JPanel menu = new JPanel(new GridLayout(6, 1, 0, 8));
        menu.setBackground(new Color(10, 18, 45));
        menu.setBorder(BorderFactory.createEmptyBorder(18, 14, 220, 14));
        menu.setPreferredSize(new Dimension(180, 0));

        addMenuButton(menu, "Flight Management", "Flights");
        addMenuButton(menu, "Book Ticket", "Book");
        addMenuButton(menu, "Reservations", "Reservations");
        addMenuButton(menu, "Payment", "Payment");
        addMenuButton(menu, "Cancel Ticket", "Cancel");

        return menu;
    }

    private void addMenuButton(JPanel menu, String text, String cardName) {
        JButton button = new JButton(text);
        Theme.styleButton(button);
        button.addActionListener(e -> {
            cardLayout.show(contentPanel, cardName);
            refreshAll();
        });
        menu.add(button);
    }

    public void refreshAll() {
        if (statsLabel != null) {
            statsLabel.setText("   Flights: " + AirlineManagementSystem.data.flights.size()
                    + "   |   Bookings: " + AirlineManagementSystem.data.reservations.size());
        }
        if (flightPanel != null) flightPanel.refreshTable();
        if (bookPanel != null) bookPanel.refreshFlights();
        if (reservationPanel != null) reservationPanel.refreshTable();
    }
}
