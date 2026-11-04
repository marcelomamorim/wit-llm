/*
 * @test
 * @summary Verifies ForkJoinPool.execute(Runnable) rejects null and runs non-null tasks
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) throws Exception {
        testNullTaskThrowsNPE();
        testRunnableTaskIsExecuted();
        testForkJoinTaskIsExecuted();
    }

    static void testNullTaskThrowsNPE() {
        ForkJoinPool pool = new ForkJoinPool(1);
        try {
            pool.execute(null);
            throw new AssertionError("Expected NullPointerException for null task");
        } catch (NullPointerException expected) {
        } finally {
            pool.shutdownNow();
        }
    }

    static void testRunnableTaskIsExecuted() throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(1);
        try {
            CountDownLatch latch = new CountDownLatch(1);
            AtomicBoolean ran = new AtomicBoolean(false);
            pool.execute(() -> {
                ran.set(true);
                latch.countDown();
            });
            if (!latch.await(60, TimeUnit.SECONDS)) {
                throw new AssertionError("Runnable task was not executed");
            }
            if (!ran.get()) {
                throw new AssertionError("Runnable task flag not set");
            }
        } finally {
            pool.shutdown();
            pool.awaitTermination(60, TimeUnit.SECONDS);
        }
    }

    static void testForkJoinTaskIsExecuted() throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(1);
        try {
            CountDownLatch latch = new CountDownLatch(1);
            ForkJoinTask<?> task = ForkJoinTask.adapt(() -> latch.countDown());
            pool.execute(task);
            if (!latch.await(60, TimeUnit.SECONDS)) {
                throw new AssertionError("ForkJoinTask was not executed");
            }
            task.join();
        } finally {
            pool.shutdown();
            pool.awaitTermination(60, TimeUnit.SECONDS);
        }
    }
}
