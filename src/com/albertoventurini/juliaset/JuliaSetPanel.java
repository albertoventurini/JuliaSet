package com.albertoventurini.juliaset;

import com.albertoventurini.juliaset.calculator.JuliaSetCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JuliaSetPanel extends JPanel {

    private JuliaSetCalculator juliaSetCalculator;
    private JuliaSetConfig config;

    public JuliaSetPanel(final JuliaSetCalculator juliaSetCalculator, final JuliaSetConfig config) {
        this.juliaSetCalculator = juliaSetCalculator;
        this.config = config;
        setPreferredSize(new Dimension(config.getWidth(), config.getHeight()));
        setBackground(Color.white);
    }

    void drawJuliaSet(Graphics2D g) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[][] iterations = juliaSetCalculator.calculate(config);

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                float i = iterations[x][y];
                int color = Color.HSBtoRGB(((config.getMaxIterations() / i) + 0.5f) % 1, 1, i > 0 ? 1 : 0);
                image.setRGB(x, y, color);
            }
        }

        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawJuliaSet(g);
    }

}