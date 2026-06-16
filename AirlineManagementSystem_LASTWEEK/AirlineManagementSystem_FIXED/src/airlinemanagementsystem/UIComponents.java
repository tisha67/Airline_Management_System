package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

class T {
    static final Color BG = new Color(7, 12, 38);
    static final Color CARD = new Color(11, 19, 55);
    static final Color FIELD = new Color(18, 30, 78);
    static final Color F_BORDER = new Color(38, 58, 130);
    static final Color F_FOCUS = new Color(80, 130, 230);
    static final Color GOLD = new Color(240, 165, 30);
    static final Color GOLD_H = new Color(255, 190, 60);
    static final Color NAV_BG = new Color(4, 8, 26);
    static final Color TEXT = Color.WHITE;
    static final Color MUTED = new Color(140, 165, 210);
    static final Color LBL = new Color(120, 145, 195);
    static final Color SUCCESS = new Color(30, 190, 110);
    static final Color DANGER = new Color(220, 55, 70);
    static final Color CYAN = new Color(0, 205, 200);
    static final Color WARN = new Color(255, 165, 0);

    static final Font F_NAV = new Font("Segoe UI", Font.BOLD, 14);
    static final Font F_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    static final Font F_LBL = new Font("Segoe UI", Font.BOLD, 10);
    static final Font F_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font F_FIELD = new Font("Segoe UI", Font.PLAIN, 14);
    static final Font F_BTN = new Font("Segoe UI", Font.BOLD, 14);
    static final Font F_MONO = new Font("Consolas", Font.PLAIN, 13);
}

class RoundBorder extends AbstractBorder {
    private Color color;
    private final int r;

    RoundBorder(Color color, int r) {
        this.color = color;
        this.r = r;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(x, y, w - 1, h - 1, r, r);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(1, 1, 1, 1);
    }
}

class BgPanel extends JPanel {
    BgPanel() {
        setLayout(null);
    }

    BgPanel(LayoutManager lm) {
        super(lm);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(T.BG);
        g2.fillRect(0, 0, getWidth(), getHeight());

        Point2D center = new Point2D.Float(getWidth() * 0.35f, getHeight() * 0.5f);
        float[] dist = {0f, 1f};
        Color[] colors = {new Color(25, 60, 150, 90), new Color(0, 0, 0, 0)};
        RadialGradientPaint rgp = new RadialGradientPaint(center, getWidth() * 0.65f, dist, colors);
        g2.setPaint(rgp);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}

class CardPanel extends JPanel {
    CardPanel(LayoutManager lm) {
        super(lm);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(T.CARD);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
        g2.setColor(new Color(50, 80, 165, 70));
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 22, 22);
        super.paintComponent(g);
    }
}

class PField extends JTextField {
    private final String placeholder;
    private final RoundBorder border;

    PField(String placeholder) {
        this.placeholder = placeholder;
        border = new RoundBorder(T.F_BORDER, 12);
        setFont(T.F_FIELD);
        setForeground(T.TEXT);
        setCaretColor(T.TEXT);
        setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 14, 10, 14)));

        addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { border.setColor(T.F_FOCUS); repaint(); }
            @Override public void focusLost(FocusEvent e) { border.setColor(T.F_BORDER); repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(T.FIELD);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        super.paintComponent(g);

        if (getText().isEmpty() && !hasFocus()) {
            g2.setFont(T.F_FIELD);
            g2.setColor(new Color(90, 115, 170));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(placeholder, 15, getHeight() / 2 + fm.getAscent() / 2 - 2);
        }
    }
}

class PPass extends JPasswordField {
    private final String placeholder;
    private final RoundBorder border;

    PPass(String placeholder) {
        this.placeholder = placeholder;
        border = new RoundBorder(T.F_BORDER, 12);
        setFont(T.F_FIELD);
        setForeground(T.TEXT);
        setCaretColor(T.TEXT);
        setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 14, 10, 14)));

        addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { border.setColor(T.F_FOCUS); repaint(); }
            @Override public void focusLost(FocusEvent e) { border.setColor(T.F_BORDER); repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(T.FIELD);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        super.paintComponent(g);

        if (getPassword().length == 0 && !hasFocus()) {
            g2.setFont(T.F_FIELD);
            g2.setColor(new Color(90, 115, 170));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(placeholder, 15, getHeight() / 2 + fm.getAscent() / 2 - 2);
        }
    }
}

class GoldBtn extends JButton {
    GoldBtn(String text) {
        super(text);
        setFont(T.F_BTN);
        setForeground(new Color(25, 15, 0));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color c = getModel().isRollover() ? T.GOLD_H : T.GOLD;
        g2.setPaint(new GradientPaint(0, 0, c, 0, getHeight(), c.darker()));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        super.paintComponent(g);
    }
}

class OutlineBtn extends JButton {
    OutlineBtn(String text) {
        super(text);
        setFont(T.F_BTN);
        setForeground(T.TEXT);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (getModel().isRollover()) {
            g2.setColor(new Color(255, 255, 255, 12));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        }
        g2.setColor(new Color(80, 105, 185));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 12, 12);
        super.paintComponent(g);
    }
}

class GradLabel extends JLabel {
    GradLabel(String text, Font font) {
        super(text);
        setFont(font);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, new Color(235, 215, 30), getWidth(), 0, new Color(0, 210, 195)));
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        g2.drawString(getText(), x, fm.getAscent());
    }

    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        return new Dimension(fm.stringWidth(getText()), fm.getHeight());
    }
}


class ComboBoxUtils {
    static <E> void style(JComboBox<E> combo) {
        combo.setFont(T.F_BODY);
        combo.setForeground(T.TEXT);
        combo.setBackground(T.FIELD);
        combo.setOpaque(false);
        combo.setMaximumRowCount(8);
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
                label.setFont(T.F_BODY);
                label.setOpaque(true);
                label.setForeground(T.TEXT);
                label.setBackground(isSelected ? T.F_FOCUS : T.FIELD);
                return label;
            }
        });
    }

    static void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }
}

class Lbl extends JLabel {
    Lbl(String text, Font font, Color color) {
        super(text);
        setFont(font);
        setForeground(color);
        setOpaque(false);
    }
}
