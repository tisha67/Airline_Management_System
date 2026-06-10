package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle(" Airline Management System - Login");
        setSize(480, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel main = new JPanel(null);
        main.setBackground(Theme.BG);
        setContentPane(main);

        JLabel title = new JLabel("✈ Airline Management System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Theme.GOLD);
        title.setBounds(40, 35, 350, 40);
        main.add(title);

        

        JLabel uLabel = new JLabel("Username");
        uLabel.setForeground(Theme.TEXT);
        uLabel.setBounds(75, 125, 300, 22);
        main.add(uLabel);

        usernameField = new JTextField();
        usernameField.setBounds(75, 150, 290, 35);
        main.add(usernameField);

        JLabel pLabel = new JLabel("Password");
        pLabel.setForeground(Theme.TEXT);
        pLabel.setBounds(75, 195, 300, 22);
        main.add(pLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(75, 220, 290, 35);
        main.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(Theme.BUTTON);
        loginBtn.setBackground(Theme.GOLD);
        loginBtn.setBounds(75, 280, 135, 38);
        main.add(loginBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(Theme.BUTTON);
        registerBtn.setBounds(230, 280, 135, 38);
        main.add(registerBtn);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        if (AirlineManagementSystem.data.users.isEmpty()) {
            AirlineManagementSystem.data.users.add(new User("Admin User", "admin", "123456"));
            AirlineManagementSystem.saveData();
        }

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        for (User user : AirlineManagementSystem.data.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                new DashboardFrame(user);
                dispose();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Invalid username or password!\nDefault: admin / 123456");
    }
}
