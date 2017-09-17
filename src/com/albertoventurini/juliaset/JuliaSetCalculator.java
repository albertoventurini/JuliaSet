package com.albertoventurini.juliaset;

public interface JuliaSetCalculator {

    int[][] calculate(
            int maxIterations,
            double zoom,
            double cx,
            double cy,
            double moveX,
            double moveY);

}
