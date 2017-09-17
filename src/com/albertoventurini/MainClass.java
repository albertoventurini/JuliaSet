package com.albertoventurini;

import com.albertoventurini.juliaset.JuliaSetConfig;
import com.albertoventurini.juliaset.calculator.JuliaSetCalculator;
import com.albertoventurini.juliaset.JuliaSetPanel;
import com.albertoventurini.juliaset.calculator.SequentialJuliaSetCalculator;
import com.albertoventurini.juliaset.calculator.ThreadedJuliaSetCalculator;

import javax.swing.*;
import java.awt.*;

public class MainClass {

    public static void main(String[] args) {

        int width = 1600;
        int height = 1000;
        JuliaSetCalculator calculator = new SequentialJuliaSetCalculator();
        JuliaSetConfig config = new JuliaSetConfig(width, height, 1000, 1.0,
                -0.7, 0.27015, 0.0, 0.0);
        JuliaSetPanel juliaSetPanel = new JuliaSetPanel(calculator, config);

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
