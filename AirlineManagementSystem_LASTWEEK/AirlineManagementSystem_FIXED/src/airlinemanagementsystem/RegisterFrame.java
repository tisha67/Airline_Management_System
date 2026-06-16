package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class RegisterFrame extends JFrame {
    RegisterFrame() {
        setTitle("Airline Management System— Create Account");
        setSize(560, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new LoginFrame();
                dispose();
            }
        });

        BgPanel bg = new BgPanel(new GridBagLayout());
        setContentPane(bg);

        CardPanel card = new CardPanel(null);
        card.setPreferredSize(new Dimension(490, 480));

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(T.TEXT);
        title.setBounds(30, 20, 430, 36);
        card.add(title);

        new Lbl("Join Airline Management System", T.F_BODY, T.MUTED) {{ setBounds(30, 56, 430, 20); card.add(this); }};

        String[] labels = {"FULL NAME", "USERNAME", "EMAIL", "PHONE", "PASSWORD", "CONFIRM PASSWORD"};
        String[] placeholders = {"Your full name", "Choose username", "you@email.com", "01XXXXXXXXX", "Min 6 chars", "Repeat password"};
        int[] xs = {30, 265, 30, 265, 30, 265};
        int[] ys = {88, 88, 162, 162, 236, 236};
        JTextField[] fields = new JTextField[6];

        for (int i = 0; i < 6; i++) {
            Lbl label = new Lbl(labels[i], T.F_LBL, T.LBL);
            label.setBounds(xs[i], ys[i], 195, 16);
            card.add(label);

            JTextField field = (i >= 4) ? new PPass(placeholders[i]) : new PField(placeholders[i]);
            field.setBounds(xs[i], ys[i] + 18, 195, 40);
            card.add(field);
            fields[i] = field;
        }

        GoldBtn createBtn = new GoldBtn("Create Account");
        createBtn.setBounds(30, 316, 430, 46);
        card.add(createBtn);

        JLabel alreadyLbl = new JLabel("Already have an account?", SwingConstants.CENTER);
        alreadyLbl.setFont(T.F_BODY);
        alreadyLbl.setForeground(T.MUTED);
        alreadyLbl.setBounds(30, 374, 430, 20);
        card.add(alreadyLbl);

        OutlineBtn backBtn = new OutlineBtn("Back to Login");
        backBtn.setBounds(30, 400, 430, 44);
        card.add(backBtn);

        createBtn.addActionListener(e -> registerUser(fields));
        backBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        bg.add(card);
        setVisible(true);
    }

    private void registerUser(JTextField[] fields) {
        String name = fields[0].getText().trim();
        String username = fields[1].getText().trim();
        String email = fields[2].getText().trim();
        String phone = fields[3].getText().trim();
        String password = new String(((JPasswordField) fields[4]).getPassword());
        String confirm = new String(((JPasswordField) fields[5]).getPassword());

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format!");
            return;
        }
        if (!phone.matches("\\d{10,}")) {
            JOptionPane.showMessageDialog(this, "Phone must be at least 10 digits!");
            return;
        }
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters!");
            return;
        }
        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        for (User user : AirlineManagementSystem.data.users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(this, "Username already taken!");
                return;
            }
        }

        User newUser = new User(name, email, phone, username, password);
        if (AirlineManagementSystem.data.users.isEmpty()) {
            newUser.setAdmin(true);
        }

        AirlineManagementSystem.data.users.add(newUser);
        AirlineManagementSystem.saveData();
        JOptionPane.showMessageDialog(this, "✓ Account created! Please sign in.");
        new LoginFrame();
        dispose();
    }
}
