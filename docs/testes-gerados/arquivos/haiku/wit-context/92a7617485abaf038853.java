/*
 * @test
 * @summary Witup-derived checks for Executors.newWorkStealingPool(int)
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testValidParallelism(1);
        testValidParallelism(4);
        testValidParallelism(0x7fff);
        testInvalidParallelism(0);
        testInvalidParallelism(-1);
        testInvalidParallelism(Integer.MIN_VALUE);
        testInvalidParallelism(0x7fff + 1);
    }

    private static void testValidParallelism(int parallelism) throws Exception {
        ExecutorService es = Executors.newWorkStealingPool(parallelism);
        try {
            if (es == null) {
                throw new AssertionError("null executor for parallelism " + parallelism);
            }
            if (es.isShutdown()) {
                throw new AssertionError("new pool already shut down, parallelism " + parallelism);
            }
            Integer result = es.submit(() -> 42).get();
            if (result.intValue() != 42) {
                throw new AssertionError("unexpected task result " + result);
            }
        } finally {
            es.shutdown();
        }
        if (!es.awaitTermination(60, TimeUnit.SECONDS)) {
            throw new AssertionError("pool did not terminate, parallelism " + parallelism);
        }
        if (!es.isTerminated()) {
            throw new AssertionError("pool not terminated, parallelism " + parallelism);
        }
    }

    private static void testInvalidParallelism(int parallelism) {
        try {
            Executors.newWorkStealingPool(parallelism);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for parallelism " + parallelism);
    }
}
