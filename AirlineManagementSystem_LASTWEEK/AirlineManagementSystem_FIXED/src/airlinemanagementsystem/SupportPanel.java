package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

class SupportPanel extends JPanel {
    SupportPanel(DashboardFrame dashboard) {
        setBackground(T.BG);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 28, 20, 28));

        JLabel h = new JLabel("💬  Customer Support");
        h.setFont(T.F_TITLE);
        h.setForeground(T.TEXT);
        add(h, BorderLayout.NORTH);

        CardPanel form = new CardPanel(null);
        form.setPreferredSize(new Dimension(340, 320));

        JLabel formTitle = new JLabel("Create Support Ticket");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        formTitle.setForeground(T.TEXT);
        formTitle.setBounds(20, 16, 300, 24);
        form.add(formTitle);

        new JLabel("ISSUE SUBJECT") {{ setFont(T.F_LBL); setForeground(T.LBL); setBounds(20, 52, 300, 16); form.add(this); }};
        PField subjectField = new PField("Brief subject");
        subjectField.setBounds(20, 70, 300, 40);
        form.add(subjectField);

        new JLabel("DESCRIPTION") {{ setFont(T.F_LBL); setForeground(T.LBL); setBounds(20, 124, 300, 16); form.add(this); }};
        JTextArea descArea = new JTextArea(4, 20);
        descArea.setFont(T.F_BODY);
        descArea.setForeground(T.TEXT);
        descArea.setBackground(T.FIELD);
        descArea.setCaretColor(T.TEXT);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBounds(20, 142, 300, 80);
        descScroll.setBorder(new RoundBorder(T.F_BORDER, 10));
        form.add(descScroll);

        GoldBtn submitBtn = new GoldBtn("💬 Submit Ticket");
        submitBtn.setBounds(20, 240, 180, 38);
        form.add(submitBtn);

        JPanel ticketList = new JPanel();
        ticketList.setBackground(T.BG);
        ticketList.setLayout(new BoxLayout(ticketList, BoxLayout.Y_AXIS));
        JScrollPane ticketScroll = new JScrollPane(ticketList);
        ticketScroll.setBackground(T.BG);
        ticketScroll.getViewport().setBackground(T.BG);
        ticketScroll.setBorder(null);

        JLabel listTitle = new JLabel("Support Tickets");
        listTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        listTitle.setForeground(T.TEXT);
        JPanel listWrap = new JPanel(new BorderLayout(0, 10));
        listWrap.setOpaque(false);
        listWrap.add(listTitle, BorderLayout.NORTH);
        listWrap.add(ticketScroll, BorderLayout.CENTER);

        Runnable refresh = () -> refreshTicketList(ticketList);
        refresh.run();

        submitBtn.addActionListener(e -> {
            String subject = subjectField.getText().trim();
            String desc = descArea.getText().trim();
            if (subject.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required!");
                return;
            }

            AirlineManagementSystem.data.supportTickets.add(new SupportTicket(subject, desc));
            AirlineManagementSystem.saveData();
            dashboard.refreshAllPanels();
            subjectField.setText("");
            descArea.setText("");
            refresh.run();
            JOptionPane.showMessageDialog(this, "✓ Support ticket submitted!");
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, wrap(form), listWrap);
        split.setDividerLocation(380);
        split.setBackground(T.BG);
        split.setBorder(null);
        split.setDividerSize(10);
        add(split, BorderLayout.CENTER);
    }

    private void refreshTicketList(JPanel ticketList) {
        ticketList.removeAll();
        java.util.List<SupportTicket> list = AirlineManagementSystem.data.supportTickets;
        if (list.isEmpty()) {
            JLabel empty = new JLabel("No tickets yet.", SwingConstants.CENTER);
            empty.setFont(T.F_BODY);
            empty.setForeground(T.MUTED);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            ticketList.add(Box.createVerticalStrut(30));
            ticketList.add(empty);
        } else {
            for (SupportTicket ticket : list) {
                CardPanel card = new CardPanel(null);
                card.setPreferredSize(new Dimension(0, 82));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 82));

                Color statusColor = ticket.status.equals("OPEN") ? T.GOLD : T.SUCCESS;

                JLabel id = new JLabel(ticket.id);
                id.setFont(new Font("Segoe UI", Font.BOLD, 11));
                id.setForeground(statusColor);
                id.setBounds(14, 6, 200, 18);
                card.add(id);

                JLabel status = new JLabel("● " + ticket.status);
                status.setFont(new Font("Segoe UI", Font.BOLD, 10));
                status.setForeground(statusColor);
                status.setBounds(300, 6, 100, 18);
                card.add(status);

                JLabel subject = new JLabel(ticket.subject);
                subject.setFont(new Font("Segoe UI", Font.BOLD, 13));
                subject.setForeground(T.TEXT);
                subject.setBounds(14, 26, 380, 18);
                card.add(subject);

                String desc = ticket.description.length() > 60 ? ticket.description.substring(0, 60) + "…" : ticket.description;
                JLabel descLabel = new JLabel(desc);
                descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                descLabel.setForeground(T.MUTED);
                descLabel.setBounds(14, 48, 380, 18);
                card.add(descLabel);

                JPanel row = new JPanel(new BorderLayout());
                row.setOpaque(false);
                row.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
                row.add(card, BorderLayout.CENTER);
                ticketList.add(row);
            }
        }
        ticketList.revalidate();
        ticketList.repaint();
    }

    private JPanel wrap(JPanel component) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(component);
        return wrapper;
    }
}
