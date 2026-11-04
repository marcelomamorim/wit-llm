/*
 * @test
 * @summary Verify ThreadPoolExecutor.setMaximumPoolSize argument validation and observable state
 * @run main ThreadPoolExecutorWitupTest
 */
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testRejectsZero();
        testRejectsNegative();
        testRejectsBelowCorePoolSize();
        testAcceptsValueEqualToCorePoolSize();
        testAcceptsValidValueAndKeepsState();
        testShrinkWithIdleWorkers();
    }

    static ThreadPoolExecutor newPool(int core, int max) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        return new ThreadPoolExecutor(core, max, 1L, TimeUnit.SECONDS, queue);
    }

    static void expectIllegalArgument(ThreadPoolExecutor pool, int value) {
        try {
            pool.setMaximumPoolSize(value);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for " + value);
    }

    static void testRejectsZero() {
        ThreadPoolExecutor pool = newPool(1, 2);
        try {
            expectIllegalArgument(pool, 0);
            if (pool.getMaximumPoolSize() != 2) {
                throw new RuntimeException("maximum pool size changed after rejected value");
            }
        } finally {
            pool.shutdown();
        }
    }

    static void testRejectsNegative() {
        ThreadPoolExecutor pool = newPool(1, 2);
        try {
            expectIllegalArgument(pool, -1);
            if (pool.getMaximumPoolSize() != 2) {
                throw new RuntimeException("maximum pool size changed after rejected value");
            }
        } finally {
            pool.shutdown();
        }
    }

    static void testRejectsBelowCorePoolSize() {
        ThreadPoolExecutor pool = newPool(2, 4);
        try {
            expectIllegalArgument(pool, 1);
            if (pool.getMaximumPoolSize() != 4) {
                throw new RuntimeException("maximum pool size changed after rejected value");
            }
        } finally {
            pool.shutdown();
        }
    }

    static void testAcceptsValueEqualToCorePoolSize() {
        ThreadPoolExecutor pool = newPool(2, 4);
        try {
            pool.setMaximumPoolSize(2);
            if (pool.getMaximumPoolSize() != 2) {
                throw new RuntimeException("expected maximum pool size 2");
            }
        } finally {
            pool.shutdown();
        }
    }

    static void testAcceptsValidValueAndKeepsState() {
        ThreadPoolExecutor pool = newPool(1, 2);
        try {
            pool.setMaximumPoolSize(5);
            if (pool.getMaximumPoolSize() != 5) {
                throw new RuntimeException("expected maximum pool size 5");
            }
            if (pool.getCorePoolSize() != 1) {
                throw new RuntimeException("core pool size must not change");
            }
            expectIllegalArgument(pool, 0);
            if (pool.getMaximumPoolSize() != 5) {
                throw new RuntimeException("maximum pool size changed after rejected value");
            }
        } finally {
            pool.shutdown();
        }
    }

    static void testShrinkWithIdleWorkers() throws InterruptedException {
        ThreadPoolExecutor pool = newPool(2, 4);
        try {
            pool.prestartAllCoreThreads();
            pool.setMaximumPoolSize(2);
            if (pool.getMaximumPoolSize() != 2) {
                throw new RuntimeException("expected maximum pool size 2 after shrink");
            }
        } finally {
            pool.shutdown();
        }
        if (!pool.awaitTermination(10, TimeUnit.SECONDS)) {
            throw new RuntimeException("pool did not terminate");
        }
    }
}
