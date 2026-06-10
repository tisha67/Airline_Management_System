package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FlightPanel extends JPanel {
    private JTextField flightNoField, sourceField, destinationField, timeField, seatsField, priceField;
    private DefaultTableModel tableModel;
    private DashboardFrame dashboard;

    public FlightPanel(DashboardFrame dashboard) {
        this.dashboard = dashboard;
        setLayout(new BorderLayout(15, 15));
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        add(buildFormPanel(), BorderLayout.WEST);
        add(buildTablePanel(), BorderLayout.CENTER);
        loadFlightsToTable();
    }

    private JPanel buildFormPanel() {
        JPanel form = new JPanel(null);
        form.setBackground(Theme.CARD);
        form.setPreferredSize(new Dimension(300, 330));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Add New Flight");
        title.setFont(Theme.TITLE);
        title.setForeground(Theme.GOLD);
        title.setBounds(20, 15, 250, 30);
        form.add(title);

        flightNoField = addField(form, "Flight No", 60);
        sourceField = addField(form, "Source", 105);
        destinationField = addField(form, "Destination", 150);
        timeField = addField(form, "Time (HH:MM)", 195);
        seatsField = addField(form, "Seats", 240);
        priceField = addField(form, "Price", 285);

        JButton addBtn = new JButton("Add Flight");
        addBtn.setFont(Theme.BUTTON);
        addBtn.setBackground(Theme.GOLD);
        addBtn.setBounds(20, 330, 240, 38);
        form.setPreferredSize(new Dimension(300, 390));
        form.add(addBtn);

        addBtn.addActionListener(e -> addFlight());
        return form;
    }

    private JTextField addField(JPanel panel, String placeholder, int y) {
        JLabel label = new JLabel(placeholder);
        label.setForeground(Theme.TEXT);
        label.setBounds(20, y, 100, 25);
        panel.add(label);

        JTextField field = new JTextField();
        field.setBounds(120, y, 140, 28);
        panel.add(field);
        return field;
    }

    private JScrollPane buildTablePanel() {
        String[] columns = {"Flight No", "Source", "Destination", "Time", "Seats", "Price"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(Theme.BODY);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    private void addFlight() {
        String flightNo = flightNoField.getText().trim();
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();
        String time = timeField.getText().trim();
        String seatsText = seatsField.getText().trim();
        String priceText = priceField.getText().trim();

        if (flightNo.isEmpty() || source.isEmpty() || destination.isEmpty() || time.isEmpty() || seatsText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try {
            int seats = Integer.parseInt(seatsText);
            double price = Double.parseDouble(priceText);

            if (seats <= 0 || price <= 0) {
                JOptionPane.showMessageDialog(this, "Seats and price must be positive!");
                return;
            }

            Flight flight = new Flight(flightNo, source, destination, time, seats, price);
            AirlineManagementSystem.data.flights.add(flight);
            AirlineManagementSystem.saveData();
            loadFlightsToTable();
            dashboard.refreshCount();
            clearFields();
            JOptionPane.showMessageDialog(this, "Flight added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Seats and price must be numeric!");
        }
    }

    private void loadFlightsToTable() {
        tableModel.setRowCount(0);
        for (Flight f : AirlineManagementSystem.data.flights) {
            tableModel.addRow(new Object[]{
                    f.getFlightNo(),
                    f.getSource(),
                    f.getDestination(),
                    f.getTime(),
                    f.getAvailableSeats() + "/" + f.getTotalSeats(),
                    "BDT " + f.getPrice()
            });
        }
    }

    private void clearFields() {
        flightNoField.setText("");
        sourceField.setText("");
        destinationField.setText("");
        timeField.setText("");
        seatsField.setText("");
        priceField.setText("");
    }
}
