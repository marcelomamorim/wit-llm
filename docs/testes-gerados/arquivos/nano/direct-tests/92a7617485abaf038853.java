/* @test    @summary Test Executors.newWorkStealingPool with valid parallelism value    @run main ExecutorsWitupTest */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testNewWorkStealingPool();
    }

    private static void testNewWorkStealingPool() {
        int validParallelism = 4;
        ExecutorService executor = Executors.newWorkStealingPool(validParallelism);
        if (executor == null) {
            throw new AssertionError("ExecutorService should not be null for valid parallelism") ;
        }
        // Submit a simple task to verify executor is operational
        executor.submit(() -> {
            System.out.println("Task executed in newWorkStealingPool");
        }).get();
        executor.shutdown();
    }
}
