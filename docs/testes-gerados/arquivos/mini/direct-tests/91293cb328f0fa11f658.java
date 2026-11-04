/*
 * @test
 * @summary Test Executors.newWorkStealingPool() returns a ForkJoinPool with parallelism equal to available processors
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        ExecutorService es = java.util.concurrent.Executors.newWorkStealingPool();
        if (!(es instanceof ForkJoinPool)) {
            throw new Error("Returned ExecutorService is not a ForkJoinPool");
        }
        ForkJoinPool fjp = (ForkJoinPool) es;
        int expectedParallelism = Runtime.getRuntime().availableProcessors();
        int actualParallelism = fjp.getParallelism();
        if (actualParallelism != expectedParallelism) {
            throw new Error("ForkJoinPool parallelism " + actualParallelism + " does not match available processors " + expectedParallelism);
        }
        es.shutdown();
    }
}
