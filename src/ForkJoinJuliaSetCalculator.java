import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinJuliaSetCalculator implements JuliaSetCalculator {

    private int width;
    private int height;
    private int[][] iterations;

    public ForkJoinJuliaSetCalculator(final int width, final int height) {
        this.width = width;
        this.height = height;
        iterations = new int[width][height];
    }

    @Override
    public int[][] calculate(int maxIterations, double zoom, double cx, double cy, double moveX, double moveY) {

        class JuliaTask extends RecursiveAction {

            private int startInclusive;
            private int endExclusive;

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
                    invokeAll(task1, task2);
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