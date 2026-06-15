package airlinemanagementsystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

public class Theme {
    public static final Color BG = new Color(12, 18, 45);
    public static final Color CARD = new Color(22, 32, 70);
    public static final Color GOLD = new Color(242, 174, 48);
    public static final Color TEXT = Color.WHITE;
    public static final Color MUTED = new Color(165, 180, 210);
    public static final Color DANGER = new Color(210, 60, 70);
    public static final Color SUCCESS = new Color(40, 170, 100);

    public static void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(GOLD);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleDangerButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(DANGER);
        button.setForeground(TEXT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setBackground(new Color(18, 28, 65));
        table.setForeground(TEXT);
        table.setGridColor(new Color(45, 60, 110));
        table.setSelectionBackground(new Color(45, 85, 150));
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(8, 14, 38));
        header.setForeground(GOLD);
    }
}
