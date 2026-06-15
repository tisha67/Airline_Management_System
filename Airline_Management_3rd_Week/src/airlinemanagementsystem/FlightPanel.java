package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FlightPanel extends JPanel {
    private DashboardFrame dashboard;
    private JTextField flightNameField, sourceField, destinationField, seatsField, timeField, priceField;
    private DefaultTableModel tableModel;

    public FlightPanel(DashboardFrame dashboard) {
        this.dashboard = dashboard;
        setLayout(new BorderLayout(15, 15));
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Flight Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Theme.GOLD);
        add(title, BorderLayout.NORTH);

        add(buildForm(), BorderLayout.WEST);
        add(buildTable(), BorderLayout.CENTER);
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(null);
        form.setPreferredSize(new Dimension(300, 0));
        form.setBackground(Theme.CARD);
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        flightNameField = addField(form, "Flight Name", 20);
        sourceField = addField(form, "Source City", 80);
        destinationField = addField(form, "Destination City", 140);
        seatsField = addField(form, "Total Seats", 200);
        timeField = addField(form, "Departure Time (HH:MM)", 260);
        priceField = addField(form, "Ticket Price", 320);

        JButton addButton = new JButton("Add Flight");
        Theme.styleButton(addButton);
        addButton.setBounds(30, 395, 230, 38);
        form.add(addButton);
        addButton.addActionListener(e -> addFlight());

        return form;
    }

    private JTextField addField(JPanel panel, String label, int y) {
        JLabel l = new JLabel(label);
        l.setForeground(Theme.TEXT);
        l.setBounds(30, y, 230, 18);
        panel.add(l);
        JTextField field = new JTextField();
        field.setBounds(30, y + 22, 230, 30);
        panel.add(field);
        return field;
    }

    private JScrollPane buildTable() {
        String[] cols = {"ID", "Flight", "Route", "Seats", "Time", "Price"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(tableModel);
        Theme.styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Theme.BG);
        return scrollPane;
    }

    private void addFlight() {
        String name = flightNameField.getText().trim();
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();
        String seatsText = seatsField.getText().trim();
        String time = timeField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || source.isEmpty() || destination.isEmpty() || seatsText.isEmpty() || time.isEmpty() || priceText.isEmpty()) {
            JOptionPaneHelper.warning(this, "All fields are required.");
            return;
        }
        if (!time.matches("\\d{1,2}:\\d{2}")) {
            JOptionPaneHelper.warning(this, "Departure time must be like 09:30.");
            return;
        }
        try {
            int seats = Integer.parseInt(seatsText);
            double price = Double.parseDouble(priceText);
            if (seats <= 0 || price <= 0) {
                JOptionPaneHelper.warning(this, "Seats and price must be positive.");
                return;
            }
            Flight flight = new Flight(name, source, destination, seats, time, price);
            AirlineManagementSystem.data.flights.add(flight);
            AirlineManagementSystem.saveData();
            clearFields();
            dashboard.refreshAll();
            JOptionPaneHelper.info(this, "Flight added successfully.");
        } catch (NumberFormatException ex) {
            JOptionPaneHelper.error(this, "Seats and price must be valid numbers.");
        }
    }

    private void clearFields() {
        flightNameField.setText("");
        sourceField.setText("");
        destinationField.setText("");
        seatsField.setText("");
        timeField.setText("");
        priceField.setText("");
    }

    public void refreshTable() {
        if (tableModel == null) return;
        tableModel.setRowCount(0);
        for (Flight f : AirlineManagementSystem.data.flights) {
            tableModel.addRow(new Object[]{
                    f.getId(), f.getName(), f.getSource() + " → " + f.getDestination(),
                    f.getAvailableSeats() + "/" + f.getTotalSeats(), f.getDepartureTime(), "BDT " + (int)f.getPrice()
            });
        }
    }
}
