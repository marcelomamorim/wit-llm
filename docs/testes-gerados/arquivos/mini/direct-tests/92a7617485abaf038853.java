/*
 * @test
 * @summary Test java.util.concurrent.Executors.newWorkStealingPool(int) basic behavior
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        // Test with parallelism = 1
        ExecutorService es1 = java.util.concurrent.Executors.newWorkStealingPool(1);
        if (!(es1 instanceof ForkJoinPool)) {
            throw new Error("Expected ForkJoinPool instance");
        }
        ForkJoinPool fjp1 = (ForkJoinPool) es1;
        if (fjp1.getParallelism() != 1) {
            throw new Error("Expected parallelism 1 but got " + fjp1.getParallelism());
        }
        es1.shutdown();

        // Test with parallelism = 4
        ExecutorService es4 = java.util.concurrent.Executors.newWorkStealingPool(4);
        if (!(es4 instanceof ForkJoinPool)) {
            throw new Error("Expected ForkJoinPool instance");
        }
        ForkJoinPool fjp4 = (ForkJoinPool) es4;
        if (fjp4.getParallelism() != 4) {
            throw new Error("Expected parallelism 4 but got " + fjp4.getParallelism());
        }
        es4.shutdown();

        // Test with parallelism = 0 (allowed by ForkJoinPool constructor, means no parallelism)
        ExecutorService es0 = java.util.concurrent.Executors.newWorkStealingPool(0);
        if (!(es0 instanceof ForkJoinPool)) {
            throw new Error("Expected ForkJoinPool instance");
        }
        ForkJoinPool fjp0 = (ForkJoinPool) es0;
        if (fjp0.getParallelism() != 0) {
            throw new Error("Expected parallelism 0 but got " + fjp0.getParallelism());
        }
        es0.shutdown();

        // Test with negative parallelism (ForkJoinPool constructor allows negative, treated as 0)
        ExecutorService esNeg = java.util.concurrent.Executors.newWorkStealingPool(-1);
        if (!(esNeg instanceof ForkJoinPool)) {
            throw new Error("Expected ForkJoinPool instance");
        }
        ForkJoinPool fjpNeg = (ForkJoinPool) esNeg;
        if (fjpNeg.getParallelism() != -1) {
            // Actually ForkJoinPool constructor does not throw, parallelism field is set as given
            // So we check that the value is exactly -1
            throw new Error("Expected parallelism -1 but got " + fjpNeg.getParallelism());
        }
        esNeg.shutdown();
    }
}
