package com.albertoventurini.juliaset.calculator;

import com.albertoventurini.juliaset.JuliaSetConfig;

import java.util.concurrent.*;


public class ExecutorServiceJuliaSetCalculator implements JuliaSetCalculator {

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

        /**
         * Each task calculates one row. I.e. if we have 'width*height' iterations,
         * we have 'width' tasks, each of which calculates the value of 'row' iterations.
         */
        class JuliaTask implements Runnable {

            private int x;

            JuliaTask(int x) {
                this.x = x;
            }

            @Override
            public void run() {
                double zx, zy;
                for(int y = 0; y < height; y++) {
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

        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for(int x = 0; x < width; x++) {
            JuliaTask task = new JuliaTask(x);
            executorService.submit(task);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (Exception ex) {
            System.out.println(ex);
        }


        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);

        return iterations;
    }



}
