//package com.albertoventurini.juliaset;
//
//public class JuliaSetUtils {
//
//    public static int[] calculateChunk(
//            final JuliaSetConfig config,
//            final int startInclusive,
//            final int endExclusive) {
//
//        double zx, zy;
//
//        int width = config.getWidth();
//        int height = config.getHeight();
//
//        for(int x = startInclusive; x < endExclusive; x++) {
//            for (int y = 0; y < height; y++) {
//                zx = 1.5 * (x - width / 2) / (0.5 * zoom * width) + moveX;
//                zy = (y - height / 2) / (0.5 * zoom * height) + moveY;
//                int i = maxIterations;
//                while (zx * zx + zy * zy < 4 && i > 0) {
//                    double tmp = zx * zx - zy * zy + cx;
//                    zy = 2.0 * zx * zy + cy;
//                    zx = tmp;
//                    i--;
//                }
//                iterations[x][y] = i;
//            }
//        }
//    }
//
//}
