package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

class FlightPanel extends JPanel {
    private final DashboardFrame dashboard;
    private JPanel listPanel;

    FlightPanel(DashboardFrame dashboard) {
        this.dashboard = dashboard;
        setBackground(T.BG);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 28, 20, 28));
        setPreferredSize(new Dimension(1020, 820));

        JLabel title = new JLabel("✈  Flight Management");
        title.setFont(T.F_TITLE);
        title.setForeground(T.TEXT);
        JLabel sub = new JLabel("Add and manage all airline flights");
        sub.setFont(T.F_BODY);
        sub.setForeground(T.MUTED);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(title);
        header.add(sub);
        add(header, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buildForm(), buildList());
        split.setDividerLocation(380);
        split.setBackground(T.BG);
        split.setBorder(null);
        split.setDividerSize(10);
        add(split, BorderLayout.CENTER);
    }

    private JPanel buildForm() {
        CardPanel card = new CardPanel(null);
        card.setPreferredSize(new Dimension(360, 580));

        JLabel h = new JLabel("Add New Flight");
        h.setFont(T.F_TITLE);
        h.setForeground(T.TEXT);
        h.setBounds(20, 16, 300, 28);
        card.add(h);

        String[] labels = {"FLIGHT NAME / NUMBER", "TOTAL SEATS", "SOURCE CITY", "DESTINATION CITY", "DEPARTURE TIME (HH:MM)", "PRICE (BDT)"};
        String[] placeholders = {"e.g. SW-101", "e.g. 180", "Dhaka", "Chittagong", "09:30", "3500"};
        int[] xs = {20, 190, 20, 190, 20, 20};
        int[] ys = {54, 54, 130, 130, 206, 280};
        int[] widths = {150, 150, 150, 150, 150, 320};
        PField[] fields = new PField[6];

        for (int i = 0; i < 6; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(T.F_LBL);
            label.setForeground(T.LBL);
            label.setBounds(xs[i], ys[i], widths[i], 16);
            card.add(label);
            fields[i] = new PField(placeholders[i]);
            fields[i].setBounds(xs[i], ys[i] + 18, widths[i], 38);
            card.add(fields[i]);
        }

        JLabel ampmLabel = new JLabel("AM / PM");
        ampmLabel.setFont(T.F_LBL);
        ampmLabel.setForeground(T.LBL);
        ampmLabel.setBounds(190, 206, 150, 16);
        card.add(ampmLabel);

        JComboBox<String> ampmBox = new JComboBox<>(new String[]{"AM", "PM"});
        ComboBoxUtils.style(ampmBox);
        ampmBox.setBounds(190, 224, 150, 38);
        card.add(ampmBox);

        JLabel classLabel = new JLabel("CLASS");
        classLabel.setFont(T.F_LBL);
        classLabel.setForeground(T.LBL);
        classLabel.setBounds(20, 342, 150, 16);
        card.add(classLabel);

        JComboBox<String> classBox = new JComboBox<>(new String[]{"Economy", "Business", "First Class"});
        ComboBoxUtils.style(classBox);
        classBox.setBounds(20, 360, 320, 38);
        card.add(classBox);

        GoldBtn addBtn = new GoldBtn("+ Add Flight");
        addBtn.setBounds(20, 414, 160, 38);
        card.add(addBtn);

        addBtn.addActionListener(e -> addFlight(fields, ampmBox, classBox));

        JPanel wrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrap.setOpaque(false);
        wrap.add(card);
        return wrap;
    }

    private JScrollPane buildList() {
        listPanel = new JPanel();
        listPanel.setBackground(T.BG);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JLabel h = new JLabel("Active Flights", SwingConstants.CENTER);
        h.setFont(T.F_TITLE);
        h.setForeground(T.TEXT);
        h.setAlignmentX(Component.CENTER_ALIGNMENT);
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(h);
        listPanel.add(Box.createVerticalStrut(10));

        refreshList();
        JScrollPane sp = new JScrollPane(listPanel);
        sp.setBackground(T.BG);
        sp.getViewport().setBackground(T.BG);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    private void addFlight(PField[] fields, JComboBox<String> ampmBox, JComboBox<String> classBox) {
        try {
            String name = fields[0].getText().trim();
            String seatsText = fields[1].getText().trim();
            String source = fields[2].getText().trim();
            String destination = fields[3].getText().trim();
            String time = fields[4].getText().trim();
            String ampm = (String) ampmBox.getSelectedItem();
            String priceText = fields[5].getText().trim();
            String cabinClass = (String) classBox.getSelectedItem();

            if (name.isEmpty() || seatsText.isEmpty() || source.isEmpty() || destination.isEmpty() || time.isEmpty() || ampm == null || priceText.isEmpty() || cabinClass == null) {
                JOptionPane.showMessageDialog(this, "All fields required!");
                return;
            }

            int seats = Integer.parseInt(seatsText);
            double price = Double.parseDouble(priceText);

            if (!time.matches("\\d{1,2}:\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Use HH:MM time format!");
                return;
            }
            if (seats <= 0 || price <= 0) {
                JOptionPane.showMessageDialog(this, "Seats and price must be positive!");
                return;
            }

            String displayTime = time + " " + ampm;
            AirlineManagementSystem.data.flights.add(new Flight(name, source, destination, seats, displayTime, price, cabinClass));
            AirlineManagementSystem.saveData();
            for (PField field : fields) field.setText("");
            ampmBox.setSelectedIndex(0);
            classBox.setSelectedIndex(0);
            refreshList();
            dashboard.refreshAllPanels();
            JOptionPane.showMessageDialog(this, "✓ Flight added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number value!");
        }
    }

    void refreshList() {
        if (listPanel == null) return;
        while (listPanel.getComponentCount() > 3) {
            listPanel.remove(listPanel.getComponentCount() - 1);
        }

        java.util.List<Flight> list = AirlineManagementSystem.data.flights;
        if (list.isEmpty()) {
            JLabel icon = new JLabel("✈", SwingConstants.CENTER);
            icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
            icon.setForeground(new Color(255, 255, 255, 40));
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel msg = new JLabel("No Flights Yet", SwingConstants.CENTER);
            msg.setFont(T.F_TITLE);
            msg.setForeground(new Color(100, 120, 170));
            msg.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel sub = new JLabel("Add your first flight", SwingConstants.CENTER);
            sub.setFont(T.F_BODY);
            sub.setForeground(T.MUTED);
            sub.setAlignmentX(Component.CENTER_ALIGNMENT);

            listPanel.add(Box.createVerticalStrut(50));
            listPanel.add(icon);
            listPanel.add(Box.createVerticalStrut(8));
            listPanel.add(msg);
            listPanel.add(sub);
        } else {
            for (Flight flight : list) {
                CardPanel card = new CardPanel(null);
                card.setPreferredSize(new Dimension(0, 80));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                JLabel name = new JLabel("✈  " + flight.name);
                name.setFont(new Font("Segoe UI", Font.BOLD, 14));
                name.setForeground(T.GOLD);
                name.setBounds(14, 8, 260, 20);
                card.add(name);

                JLabel route = new JLabel(flight.source + " → " + flight.dest);
                route.setFont(T.F_BODY);
                route.setForeground(T.TEXT);
                route.setBounds(14, 30, 260, 18);
                card.add(route);

                JLabel info = new JLabel("Seats: " + flight.seats + "/" + flight.totalSeats + "  |  Dep: " + flight.time + "  |  Class: " + flight.cabinClass + "  |  BDT " + String.format("%.0f", flight.price));
                info.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                info.setForeground(T.MUTED);
                info.setBounds(14, 50, 420, 16);
                card.add(info);

                JLabel occ = new JLabel(String.format("%.0f%%", flight.getOccupancyPercentage()) + " booked");
                occ.setFont(new Font("Segoe UI", Font.BOLD, 11));
                occ.setForeground(T.CYAN);
                occ.setBounds(360, 30, 120, 18);
                card.add(occ);

                JPanel row = new JPanel(new BorderLayout());
                row.setOpaque(false);
                row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
                row.add(card, BorderLayout.CENTER);
                listPanel.add(row);
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }
}
