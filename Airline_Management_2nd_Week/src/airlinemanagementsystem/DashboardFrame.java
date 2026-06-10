package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private User currentUser;
    private JLabel flightCountLabel;

    public DashboardFrame(User user) {
        this.currentUser = user;
        setTitle("Airline Management System - Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.BG);
        setContentPane(root);

        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(new Color(5, 10, 30));
        nav.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel logo = new JLabel("✈ Airline Management System");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(Theme.TEXT);
        nav.add(logo, BorderLayout.WEST);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            AirlineManagementSystem.saveData();
            new LoginFrame();
            dispose();
        });
        nav.add(logout, BorderLayout.EAST);
        root.add(nav, BorderLayout.NORTH);

        JPanel hero = new JPanel(null);
        hero.setBackground(Theme.BG);
        hero.setPreferredSize(new Dimension(0, 120));

        JLabel welcome = new JLabel("Welcome, " + currentUser.getName());
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcome.setForeground(Theme.GOLD);
        welcome.setBounds(30, 20, 500, 35);
        hero.add(welcome);

        JLabel info = new JLabel(" Dashboard and Flight Management GUI");
        info.setFont(Theme.BODY);
        info.setForeground(Theme.MUTED);
        info.setBounds(30, 58, 500, 25);
        hero.add(info);

        flightCountLabel = new JLabel("Total Flights: " + AirlineManagementSystem.data.flights.size());
        flightCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        flightCountLabel.setForeground(Theme.TEXT);
        flightCountLabel.setBounds(650, 35, 200, 35);
        hero.add(flightCountLabel);

        root.add(hero, BorderLayout.CENTER);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(Theme.BODY);
        FlightPanel flightPanel = new FlightPanel(this);
        tabs.addTab("Flight Management", flightPanel);
        tabs.addTab("About Week 3", buildAboutPanel());
        root.add(tabs, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void refreshCount() {
        flightCountLabel.setText("Total Flights: " + AirlineManagementSystem.data.flights.size());
    }

    private JPanel buildAboutPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BG);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(Theme.BODY);
        area.setBackground(Theme.BG);
        area.setForeground(Theme.TEXT);
        area.setText("Week 3 Development Summary\n\n" +
                "• Java Swing GUI introduced.\n" +
                "• Login and registration converted into windows.\n" +
                "• Dashboard screen created.\n" +
                "• Flight add and flight list features added.\n" +
                "• Data is still stored using file handling.\n\n" +
                "Pending for next weeks:\n" +
                "• Ticket booking\n" +
                "• Payment system\n" +
                "• Cancellation\n" +
                "• Staff, support and report panel");
        area.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.add(area, BorderLayout.CENTER);
        return panel;
    }
}
