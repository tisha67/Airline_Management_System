package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

class StaffPanel extends JPanel {
    StaffPanel(DashboardFrame dashboard) {
        setBackground(T.BG);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 28, 20, 28));

        JLabel h = new JLabel("👥  Staff Management");
        h.setFont(T.F_TITLE);
        h.setForeground(T.TEXT);
        add(h, BorderLayout.NORTH);

        CardPanel form = new CardPanel(null);
        form.setPreferredSize(new Dimension(340, 320));

        JLabel formTitle = new JLabel("Add Staff Member");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        formTitle.setForeground(T.TEXT);
        formTitle.setBounds(20, 16, 300, 24);
        form.add(formTitle);

        new JLabel("STAFF NAME") {{ setFont(T.F_LBL); setForeground(T.LBL); setBounds(20, 52, 300, 16); form.add(this); }};
        PField nameField = new PField("Full name");
        nameField.setBounds(20, 70, 300, 40);
        form.add(nameField);

        new JLabel("ROLE / DESIGNATION") {{ setFont(T.F_LBL); setForeground(T.LBL); setBounds(20, 124, 300, 16); form.add(this); }};
        PField roleField = new PField("e.g. Pilot, Engineer");
        roleField.setBounds(20, 142, 300, 40);
        form.add(roleField);

        new JLabel("DEPARTMENT") {{ setFont(T.F_LBL); setForeground(T.LBL); setBounds(20, 196, 300, 16); form.add(this); }};
        PField deptField = new PField("e.g. Operations, Ground");
        deptField.setBounds(20, 214, 300, 40);
        form.add(deptField);

        GoldBtn addBtn = new GoldBtn("+ Add Staff");
        addBtn.setBounds(20, 268, 150, 38);
        form.add(addBtn);

        JPanel staffList = new JPanel();
        staffList.setBackground(T.BG);
        staffList.setLayout(new BoxLayout(staffList, BoxLayout.Y_AXIS));
        JScrollPane sp = new JScrollPane(staffList);
        sp.setBackground(T.BG);
        sp.getViewport().setBackground(T.BG);
        sp.setBorder(null);

        JLabel listTitle = new JLabel("Staff Directory");
        listTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        listTitle.setForeground(T.TEXT);
        JPanel listWrap = new JPanel(new BorderLayout(0, 10));
        listWrap.setOpaque(false);
        listWrap.add(listTitle, BorderLayout.NORTH);
        listWrap.add(sp, BorderLayout.CENTER);

        Runnable refresh = () -> refreshStaffList(staffList);
        refresh.run();

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String role = roleField.getText().trim();
            String department = deptField.getText().trim();

            if (name.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Role are required!");
                return;
            }

            AirlineManagementSystem.data.staffMembers.add(new Staff(name, role, department.isEmpty() ? "General" : department));
            AirlineManagementSystem.saveData();
            dashboard.refreshAllPanels();
            nameField.setText("");
            roleField.setText("");
            deptField.setText("");
            refresh.run();
            JOptionPane.showMessageDialog(this, "✓ Staff member added!");
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, wrap(form), listWrap);
        split.setDividerLocation(380);
        split.setBackground(T.BG);
        split.setBorder(null);
        split.setDividerSize(10);
        add(split, BorderLayout.CENTER);
    }

    private void refreshStaffList(JPanel staffList) {
        staffList.removeAll();
        if (AirlineManagementSystem.data.staffMembers.isEmpty()) {
            JLabel empty = new JLabel("No staff added yet.", SwingConstants.CENTER);
            empty.setFont(T.F_BODY);
            empty.setForeground(T.MUTED);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            staffList.add(Box.createVerticalStrut(30));
            staffList.add(empty);
        } else {
            for (Staff staff : AirlineManagementSystem.data.staffMembers) {
                CardPanel card = new CardPanel(null);
                card.setPreferredSize(new Dimension(0, 72));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));

                JLabel name = new JLabel("👤  " + staff.name);
                name.setFont(new Font("Segoe UI", Font.BOLD, 13));
                name.setForeground(T.TEXT);
                name.setBounds(14, 6, 350, 20);
                card.add(name);

                JLabel role = new JLabel(staff.role + (staff.department != null && !staff.department.equals("General") ? "  |  " + staff.department : ""));
                role.setFont(T.F_BODY);
                role.setForeground(T.GOLD);
                role.setBounds(14, 28, 350, 18);
                card.add(role);

                String joined = staff.joinDate == null ? "-" : staff.joinDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                JLabel id = new JLabel(staff.id + "  |  Joined: " + joined);
                id.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                id.setForeground(T.MUTED);
                id.setBounds(14, 50, 350, 14);
                card.add(id);

                JPanel row = new JPanel(new BorderLayout());
                row.setOpaque(false);
                row.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
                row.add(card, BorderLayout.CENTER);
                staffList.add(row);
            }
        }
        staffList.revalidate();
        staffList.repaint();
    }

    private JPanel wrap(JPanel component) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(component);
        return wrapper;
    }
}
