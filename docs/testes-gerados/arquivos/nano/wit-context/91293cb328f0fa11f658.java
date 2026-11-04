/*
 * @test
 * @summary Testa o método Executors.newWorkStealingPool() para comportamento observável.
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testNewWorkStealingPoolNormal();
        testNewWorkStealingPoolNullFactory();
        testNewWorkStealingPoolInvalidProcessors();
        testNewWorkStealingPoolInvalidKeepAlive();
    }

    static void testNewWorkStealingPoolNormal() {
        try {
            ExecutorService es = Executors.newWorkStealingPool();
            if (!(es instanceof ForkJoinPool)) {
                throw new AssertionError("Expected ForkJoinPool instance");
            }
            ForkJoinPool fjp = (ForkJoinPool) es;
            int processors = Runtime.getRuntime().availableProcessors();
            if (fjp.getParallelism() != processors) {
                throw new AssertionError("Parallelism mismatch: expected " + processors + " but was " + fjp.getParallelism());
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception in normal case", t);
        }
    }

    static void testNewWorkStealingPoolNullFactory() {
        try {
            ForkJoinPool pool = new ForkJoinPool(
                Runtime.getRuntime().availableProcessors(),
                null,
                null,
                true
            );
            if (!(pool instanceof ForkJoinPool)) {
                throw new AssertionError("Expected ForkJoinPool instance");
            }
        } catch (NullPointerException e) {
            // Expected if internal code does not handle null factory
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception when factory is null", t);
        }
    }

    static void testNewWorkStealingPoolInvalidProcessors() {
        try {
            int invalidProcessors = -1;
            ForkJoinPool pool = new ForkJoinPool(
                invalidProcessors,
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                null,
                true
            );
            throw new AssertionError("Expected IllegalArgumentException for invalid processors");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for invalid processors", t);
        }
    }

    static void testNewWorkStealingPoolInvalidKeepAlive() {
        try {
            ForkJoinPool pool = new ForkJoinPool(
                Runtime.getRuntime().availableProcessors(),
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                null,
                true,
                0,
                0,
                60000L,
                null,
                null
            );
            throw new AssertionError("Expected IllegalArgumentException for invalid keepAlive");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception for invalid keepAlive", t);
        }
    }
}
