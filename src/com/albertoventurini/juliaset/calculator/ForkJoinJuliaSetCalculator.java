package com.albertoventurini.juliaset.calculator;

import com.albertoventurini.juliaset.JuliaSetConfig;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinJuliaSetCalculator implements JuliaSetCalculator {

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

        class JuliaTask extends RecursiveAction {

            private int startInclusive;
            private int endExclusive;

            // When the problem size is too small to be divided into subproblems
            private final int THRESHOLD = 10;

            JuliaTask(int startInclusive, int endExclusive) {
                this.startInclusive = startInclusive;
                this.endExclusive = endExclusive;
            }

            private void computeDirectly() {
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

            @Override
            protected void compute() {
                if(endExclusive - startInclusive < THRESHOLD) {
                    computeDirectly();
                } else {
                    int middle = (startInclusive + endExclusive) / 2;
                    JuliaTask task1 = new JuliaTask(startInclusive, middle);
                    JuliaTask task2 = new JuliaTask(middle, endExclusive);
                    //invokeAll(task1, task2);
                    task1.fork();
                    task2.compute();
                    task1.join(); // Interestingly, if I comment out this join, it seems to work ok???
                }
            }
        }

        long startTime = System.currentTimeMillis();

        JuliaTask task = new JuliaTask(0, width);
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", Integer.toString(8));
        ForkJoinPool.commonPool().invoke(task);

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        return iterations;

    }

}
