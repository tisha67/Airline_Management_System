package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField nameField, usernameField;
    private JPasswordField passwordField;

    public RegisterFrame() {
        setTitle("Airline Management System - Register");
        setSize(450, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel main = new JPanel(null);
        main.setBackground(Theme.BG);
        setContentPane(main);

        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(Theme.TITLE);
        title.setForeground(Theme.GOLD);
        title.setBounds(40, 35, 350, 35);
        main.add(title);

        JLabel nLabel = new JLabel("Full Name");
        nLabel.setForeground(Theme.TEXT);
        nLabel.setBounds(75, 100, 300, 22);
        main.add(nLabel);
        nameField = new JTextField();
        nameField.setBounds(75, 125, 290, 35);
        main.add(nameField);

        JLabel uLabel = new JLabel("Username");
        uLabel.setForeground(Theme.TEXT);
        uLabel.setBounds(75, 170, 300, 22);
        main.add(uLabel);
        usernameField = new JTextField();
        usernameField.setBounds(75, 195, 290, 35);
        main.add(usernameField);

        JLabel pLabel = new JLabel("Password");
        pLabel.setForeground(Theme.TEXT);
        pLabel.setBounds(75, 240, 300, 22);
        main.add(pLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(75, 265, 290, 35);
        main.add(passwordField);

        JButton createBtn = new JButton("Create Account");
        createBtn.setFont(Theme.BUTTON);
        createBtn.setBackground(Theme.GOLD);
        createBtn.setBounds(75, 325, 150, 38);
        main.add(createBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(Theme.BUTTON);
        backBtn.setBounds(245, 325, 120, 38);
        main.add(backBtn);

        createBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

    private void register() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters!");
            return;
        }
        for (User user : AirlineManagementSystem.data.users) {
            if (user.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists!");
                return;
            }
        }

        AirlineManagementSystem.data.users.add(new User(name, username, password));
        AirlineManagementSystem.saveData();
        JOptionPane.showMessageDialog(this, "Account created successfully!");
        new LoginFrame();
        dispose();
    }
}
