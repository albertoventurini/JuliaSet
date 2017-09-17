package com.albertoventurini.juliaset.calculator;

import com.albertoventurini.juliaset.JuliaSetConfig;
import com.albertoventurini.juliaset.calculator.JuliaSetCalculator;

public class ThreadedJuliaSetCalculator implements JuliaSetCalculator {

    @Override
    public int[][] calculate(final JuliaSetConfig config) {

        int width = config.getWidth();
        int height = config.getHeight();
        int[][] iterations = new int[width][height];
        double zoom = config.getZoom();
        double moveX = config.getMoveX();
        double moveY = config.getMoveY();
        int maxIterations = config.getMaxIterations();
        double cx = config.getCx();
        double cy = config.getCy();

        class JuliaTask implements Runnable {

            private int startInclusive;
            private int endExclusive;

            JuliaTask(int startInclusive, int endExclusive) {
                this.startInclusive = startInclusive;
                this.endExclusive = endExclusive;
            }

            @Override
            public void run() {
                double zx, zy;

                for(int x = startInclusive; x < endExclusive; x++) {
                    for (int y = 0; y < height; y++) {
                        zx = 1.5 * (x - width / 2) / (0.5 * zoom * width) + moveX;
                        zy = (y - height / 2) / (0.5 * zoom * height) + moveY;
                        int i = maxIterations;
                        while (zx * zx + zy * zy < 4 && i > 0) {
                            double tmp = zx * zx - zy * zy + cx;
                            zy = 2.0 * zx * zy + cy;
                            zx = tmp;
                            i--;
                        }
                        iterations[x][y] = i;
                    }
                }
            }
        }

        long startTime = System.currentTimeMillis();

        int NUM_THREADS = 128;
        Thread[] threads = new Thread[NUM_THREADS];
        int step = width / NUM_THREADS;

        for(int i = 0; i < NUM_THREADS; i++) {
            int start = i * step;
            int end = (i + 1) * step;
            if(end > width || i == NUM_THREADS - 1) end = width;
            threads[i] = new Thread(new JuliaTask(start, end));
            threads[i].start();
        }

        for(int i = 0; i < NUM_THREADS; i++) {
            try {
                threads[i].join();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        return iterations;
    }

}
