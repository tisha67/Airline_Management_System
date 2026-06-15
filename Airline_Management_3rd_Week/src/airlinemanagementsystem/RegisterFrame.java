package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterFrame extends JFrame {
    private JTextField nameField, emailField, phoneField, usernameField;
    private JPasswordField passwordField, confirmField;

    public RegisterFrame() {
        setTitle("Airline Management System - Register");
        setSize(470, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new LoginFrame();
                dispose();
            }
        });

        JPanel panel = new JPanel(null);
        panel.setBackground(Theme.BG);
        setContentPane(panel);

        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Theme.GOLD);
        title.setBounds(30, 24, 390, 36);
        panel.add(title);

        nameField = addInput(panel, "Full Name", 70);
        usernameField = addInput(panel, "Username", 130);
        emailField = addInput(panel, "Email", 190);
        phoneField = addInput(panel, "Phone", 250);
        passwordField = addPassword(panel, "Password", 310);
        confirmField = addPassword(panel, "Confirm Password", 370);

        JButton createButton = new JButton("Create Account");
        Theme.styleButton(createButton);
        createButton.setBounds(65, 430, 160, 36);
        panel.add(createButton);

        JButton backButton = new JButton("Back");
        Theme.styleDangerButton(backButton);
        backButton.setBounds(245, 430, 145, 36);
        panel.add(backButton);

        createButton.addActionListener(e -> register());
        backButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

    private JTextField addInput(JPanel panel, String label, int y) {
        JLabel l = new JLabel(label);
        l.setForeground(Theme.TEXT);
        l.setBounds(65, y, 140, 20);
        panel.add(l);
        JTextField field = new JTextField();
        field.setBounds(205, y, 185, 30);
        panel.add(field);
        return field;
    }

    private JPasswordField addPassword(JPanel panel, String label, int y) {
        JLabel l = new JLabel(label);
        l.setForeground(Theme.TEXT);
        l.setBounds(65, y, 140, 20);
        panel.add(l);
        JPasswordField field = new JPasswordField();
        field.setBounds(205, y, 185, 30);
        panel.add(field);
        return field;
    }

    private void register() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String pass = new String(passwordField.getPassword());
        String confirm = new String(confirmField.getPassword());

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty()) {
            JOptionPaneHelper.warning(this, "All fields are required.");
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPaneHelper.warning(this, "Invalid email format.");
            return;
        }
        if (pass.length() < 6) {
            JOptionPaneHelper.warning(this, "Password must be at least 6 characters.");
            return;
        }
        if (!pass.equals(confirm)) {
            JOptionPaneHelper.warning(this, "Passwords do not match.");
            return;
        }
        for (User user : AirlineManagementSystem.data.users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                JOptionPaneHelper.warning(this, "Username already exists.");
                return;
            }
        }

        User user = new User(name, email, phone, username, pass);
        AirlineManagementSystem.data.users.add(user);
        AirlineManagementSystem.saveData();
        JOptionPaneHelper.info(this, "Account created successfully. Please login.");
        new LoginFrame();
        dispose();
    }
}
