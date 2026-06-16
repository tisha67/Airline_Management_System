package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

class LoginFrame extends JFrame {
    private PField userField;
    private PPass passField;

    LoginFrame() {
        setTitle("Airline Management System — Sign In");
        setSize(520, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        BgPanel bg = new BgPanel(new GridBagLayout());
        setContentPane(bg);

        CardPanel card = new CardPanel(null);
        card.setPreferredSize(new Dimension(380, 500));

        JLabel plane = new JLabel("✈", SwingConstants.CENTER);
        plane.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        plane.setForeground(Color.WHITE);
        plane.setBounds(0, 24, 380, 48);
        card.add(plane);

        GradLabel title = new GradLabel("Airline Management System", new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(20, 76, 350, 36);
        card.add(title);

        

        new Lbl("USERNAME", T.F_LBL, T.LBL) {{ setBounds(30, 150, 320, 16); card.add(this); }};
        userField = new PField("Enter username");
        userField.setBounds(30, 168, 320, 44);
        card.add(userField);

        new Lbl("PASSWORD", T.F_LBL, T.LBL) {{ setBounds(30, 224, 320, 16); card.add(this); }};
        passField = new PPass("Enter password");
        passField.setBounds(30, 242, 320, 44);
        card.add(passField);

        GoldBtn signIn = new GoldBtn("Sign In");
        signIn.setBounds(30, 306, 320, 46);
        card.add(signIn);

        JLabel divLbl = new JLabel("— or —", SwingConstants.CENTER);
        divLbl.setFont(T.F_BODY);
        divLbl.setForeground(new Color(90, 115, 165));
        divLbl.setBounds(30, 364, 320, 20);
        card.add(divLbl);

        OutlineBtn createAcc = new OutlineBtn("Create Account");
        createAcc.setBounds(30, 393, 320, 44);
        card.add(createAcc);

        signIn.addActionListener(e -> doLogin());
        passField.addActionListener(e -> doLogin());
        createAcc.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        bg.add(card);
        setVisible(true);
    }

    private void doLogin() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (User user : AirlineManagementSystem.data.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                new DashboardFrame(user);
                dispose();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        passField.setText("");
    }
}
