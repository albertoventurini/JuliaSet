package com.albertoventurini.juliaset;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JuliaSetPanel extends JPanel {
    private static int maxIter = 1000;
    private static double zoom = 1;
    private double cY, cX;

    private JuliaSetCalculator juliaSetCalculator;

    public JuliaSetPanel(final JuliaSetCalculator juliaSetCalculator, final int width, final int height) {
        this.juliaSetCalculator = juliaSetCalculator;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.white);
    }

    void drawJuliaSet(Graphics2D g) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        cX = -0.7;
        cY = 0.27015;
        double moveX = 0, moveY = 0;

        int[][] iterations = juliaSetCalculator.calculate(maxIter, zoom, cX, cY, moveX, moveY);

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                float i = iterations[x][y];
                int color = Color.HSBtoRGB(((maxIter / i) + 0.5f) % 1, 1, i > 0 ? 1 : 0);
                image.setRGB(x, y, color);
            }
        }

        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawJuliaSet(g);
    }

}