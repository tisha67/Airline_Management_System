package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;

class DashboardFrame extends JFrame {
    private final User user;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentArea = new JPanel(cardLayout);
    private final JLabel[] statLabels = new JLabel[4];
    private final String[] tabNames = {"Flights", "Book Ticket", "Reservations", "Payment", "Cancel", "Staff", "Support", "Report"};
    private JButton[] tabBtns;
    private int activeTab = 0;

    private BookPanel bookPanel;
    private ReservationPanel reservationPanel;
    private ReportPanel reportPanel;

    DashboardFrame(User user) {
        this.user = user;
        setTitle("Airline Management System");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(T.BG);
        setContentPane(root);
        root.add(buildNav(), BorderLayout.NORTH);

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(T.BG);
        topArea.add(buildHero(), BorderLayout.NORTH);
        topArea.add(buildTabBar(), BorderLayout.CENTER);

        contentArea.setBackground(T.BG);
        buildPanels();

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(T.BG);
        body.add(topArea, BorderLayout.NORTH);
        body.add(contentArea, BorderLayout.CENTER);

        root.add(body, BorderLayout.CENTER);
        switchTab(0);
        setVisible(true);
    }

    private JPanel buildNav() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(T.NAV_BG);
        nav.setPreferredSize(new Dimension(0, 52));
        nav.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel logo = new JLabel("  ✈  Airline Management System");
        logo.setFont(T.F_NAV);
        logo.setForeground(T.TEXT);
        nav.add(logo, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        right.setOpaque(false);

        right.add(new JPanel() {
            { setOpaque(false); setPreferredSize(new Dimension(34, 34)); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(T.SUCCESS);
                g2.fillOval(0, 0, 33, 33);
                g2.setColor(T.TEXT);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                String initial = user.getName().isEmpty() ? "U" : String.valueOf(user.getName().charAt(0)).toUpperCase();
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(initial, (33 - fm.stringWidth(initial)) / 2, (33 + fm.getAscent() - fm.getDescent()) / 2);
            }
        });

        JLabel uname = new JLabel(user.getName());
        uname.setFont(T.F_BODY);
        uname.setForeground(T.TEXT);
        right.add(uname);

        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logout.setForeground(T.TEXT);
        logout.setBackground(T.DANGER);
        logout.setBorder(BorderFactory.createEmptyBorder(5, 14, 5, 14));
        logout.setFocusPainted(false);
        logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logout.addActionListener(e -> JOptionPane.showMessageDialog(this, "Logout clicked"));
        right.add(logout);

        nav.add(right, BorderLayout.EAST);
        return nav;
    }

    private JPanel buildHero() {
        JPanel hero = new JPanel(null);
        hero.setBackground(new Color(10, 18, 55));
        hero.setPreferredSize(new Dimension(0, 130));

        JLabel welcome = new JLabel("Welcome back, ");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcome.setForeground(T.TEXT);
        welcome.setBounds(28, 18, 200, 36);
        hero.add(welcome);

        JLabel userLabel = new JLabel(user.getName());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        userLabel.setForeground(T.GOLD);
        userLabel.setBounds(228, 18, 300, 36);
        hero.add(userLabel);

        JLabel sub = new JLabel("Manage flights, bookings, staff and support from one dashboard");
        sub.setFont(T.F_BODY);
        sub.setForeground(T.MUTED);
        sub.setBounds(28, 54, 540, 20);
        hero.add(sub);

        String[] statNames = {"FLIGHTS", "BOOKINGS", "STAFF", "TICKETS"};
        int[] sx = {28, 120, 215, 310};
        for (int i = 0; i < 4; i++) {
            JLabel number = new JLabel("0");
            number.setFont(new Font("Segoe UI", Font.BOLD, 18));
            number.setForeground(T.CYAN);
            number.setBounds(sx[i], 82, 80, 22);
            statLabels[i] = number;
            hero.add(number);

            JLabel name = new JLabel(statNames[i]);
            name.setFont(new Font("Segoe UI", Font.BOLD, 10));
            name.setForeground(T.MUTED);
            name.setBounds(sx[i], 104, 80, 16);
            hero.add(name);
        }

        JLabel watermark = new JLabel("➤");
        watermark.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 90));
        watermark.setForeground(new Color(255, 255, 255, 20));
        watermark.setBounds(750, 10, 300, 110);
        hero.add(watermark);

        return hero;
    }

    private JPanel buildTabBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bar.setBackground(new Color(8, 14, 42));
        bar.setPreferredSize(new Dimension(0, 44));

        String[] icons = {"✈", "🎫", "📋", "💳", "❌", "👥", "💬", "📊"};
        tabBtns = new JButton[tabNames.length];
        for (int i = 0; i < tabNames.length; i++) {
            final int index = i;
            JButton btn = new JButton(" " + icons[i] + "  " + tabNames[i] + " ") {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    if (index == activeTab) {
                        g2.setColor(new Color(240, 165, 30, 30));
                        g2.fillRect(0, 0, getWidth(), getHeight());
                    } else if (getModel().isRollover()) {
                        g2.setColor(new Color(255, 255, 255, 8));
                        g2.fillRect(0, 0, getWidth(), getHeight());
                    }
                    super.paintComponent(g);
                    if (index == activeTab) {
                        g2.setColor(T.GOLD);
                        g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                    }
                }
            };
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setForeground(i == activeTab ? T.GOLD : T.MUTED);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(115, 44));
            btn.addActionListener(e -> switchTab(index));
            tabBtns[i] = btn;
            bar.add(btn);
        }
        return bar;
    }


    private JScrollPane wrapScrollable(JComponent component) {
        JScrollPane sp = new JScrollPane(component, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(null);
        sp.setBackground(T.BG);
        sp.getViewport().setBackground(T.BG);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    private void buildPanels() {
        contentArea.add(wrapScrollable(new FlightPanel(this)), "Flights");
        bookPanel = new BookPanel(this);
        contentArea.add(wrapScrollable(bookPanel), "Book Ticket");
        reservationPanel = new ReservationPanel();
        contentArea.add(wrapScrollable(reservationPanel), "Reservations");
        contentArea.add(wrapScrollable(new PaymentPanel(this)), "Payment");
        contentArea.add(wrapScrollable(new CancelPanel(this)), "Cancel");
        contentArea.add(wrapScrollable(new StaffPanel(this)), "Staff");
        contentArea.add(wrapScrollable(new SupportPanel(this)), "Support");
        reportPanel = new ReportPanel();
        contentArea.add(wrapScrollable(reportPanel), "Report");
    }

    void switchTab(int index) {
        activeTab = index;
        for (int i = 0; i < tabBtns.length; i++) {
            tabBtns[i].setForeground(i == index ? T.GOLD : T.MUTED);
            tabBtns[i].repaint();
        }
        cardLayout.show(contentArea, tabNames[index]);
        refreshAllPanels();
    }

    void refreshStats() {
        AppData data = AirlineManagementSystem.data;
        statLabels[0].setText(String.valueOf(data.flights.size()));
        statLabels[1].setText(String.valueOf(data.reservations.size()));
        statLabels[2].setText(String.valueOf(data.staffMembers.size()));
        statLabels[3].setText(String.valueOf(data.supportTickets.size()));
    }

    void refreshAllPanels() {
        refreshStats();
        if (bookPanel != null) bookPanel.refresh();
        if (reservationPanel != null) reservationPanel.refresh();
        if (reportPanel != null) reportPanel.refresh();
    }
}
