/*
 * @test
 * @summary ForkJoinPool.submit(Runnable) rejects null and runs non-null runnables
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicInteger;

public class ForkJoinPoolWitupTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ForkJoinPool pool = new ForkJoinPool(1);
        try {
            boolean npe = false;
            try {
                pool.submit((Runnable) null);
            } catch (NullPointerException expected) {
                npe = true;
            }
            if (!npe) {
                throw new AssertionError();
            }

            AtomicInteger counter = new AtomicInteger();
            ForkJoinTask<?> task = pool.submit((Runnable) () -> { counter.incrementAndGet(); });
            if (task == null) {
                throw new AssertionError();
            }
            task.get();
            if (counter.get() != 1) {
                throw new AssertionError();
            }
        } finally {
            pool.shutdown();
        }
    }
}
