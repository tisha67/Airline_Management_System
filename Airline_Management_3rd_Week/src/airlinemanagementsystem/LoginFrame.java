package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle(" Airline Management System - Login");
        setSize(430, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(null);
        panel.setBackground(Theme.BG);
        setContentPane(panel);

        JLabel title = new JLabel(" Airline Management System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Theme.GOLD);
        title.setBounds(30, 28, 360, 40);
        panel.add(title);

        

        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Theme.TEXT);
        userLabel.setBounds(70, 110, 280, 20);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(70, 132, 280, 34);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Theme.TEXT);
        passLabel.setBounds(70, 178, 280, 20);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(70, 200, 280, 34);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        Theme.styleButton(loginButton);
        loginButton.setBounds(70, 252, 130, 36);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        Theme.styleButton(registerButton);
        registerButton.setBounds(220, 252, 130, 36);
        panel.add(registerButton);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });
        passwordField.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPaneHelper.warning(this, "Please enter username and password.");
            return;
        }
        for (User user : AirlineManagementSystem.data.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                new DashboardFrame(user);
                dispose();
                return;
            }
        }
        JOptionPaneHelper.error(this, "Invalid username or password.");
        passwordField.setText("");
    }
}
