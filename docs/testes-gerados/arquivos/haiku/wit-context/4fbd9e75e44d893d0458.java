/*
 * @test
 * @summary Verifies ThreadPoolExecutor.getActiveCount reports busy workers
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            if (pool.getActiveCount() != 0) {
                throw new RuntimeException("Expected 0 active threads on new pool, got "
                        + pool.getActiveCount());
            }

            final CountDownLatch started = new CountDownLatch(1);
            final CountDownLatch release = new CountDownLatch(1);
            pool.execute(new Runnable() {
                public void run() {
                    started.countDown();
                    try {
                        release.await();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });

            if (!started.await(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("Task did not start");
            }
            if (pool.getActiveCount() != 1) {
                throw new RuntimeException("Expected 1 active thread while task runs, got "
                        + pool.getActiveCount());
            }

            release.countDown();
            pool.shutdown();
            if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
                throw new RuntimeException("Pool did not terminate");
            }
            if (pool.getActiveCount() != 0) {
                throw new RuntimeException("Expected 0 active threads after termination, got "
                        + pool.getActiveCount());
            }
        } finally {
            pool.shutdownNow();
        }
    }
}
