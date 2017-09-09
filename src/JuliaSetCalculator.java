import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class JuliaSetCalculator {
    private int width;
    private int height;
    private int[][] iterations;

    public JuliaSetCalculator(final int width, final int height) {
        this.width = width;
        this.height = height;
        iterations = new int[width][height];
    }

    public int[][] calculate(
            final int maxIterations,
            final double zoom,
            final double cx,
            final double cy,
            final double moveX,
            final double moveY) {

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

        ExecutorService executorService = Executors.newWorkStealingPool(8);
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
