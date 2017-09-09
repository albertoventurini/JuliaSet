import javax.swing.*;
import java.awt.*;

public class MainClass {

    public static void main(String[] args) {

        int width = 1200;
        int height = 800;
        JuliaSetCalculator juliaSetCalculator = new JuliaSetCalculator(width, height);
        JuliaSetPanel juliaSetPanel = new JuliaSetPanel(juliaSetCalculator, width, height);

        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Julia Set");
            f.setResizable(false);
            f.add(juliaSetPanel, BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }

}