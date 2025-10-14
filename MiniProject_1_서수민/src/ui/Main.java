package ui;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Swing GUI는 Event Dispatch Thread(EDT)에서 실행해야 안전합니다.
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}