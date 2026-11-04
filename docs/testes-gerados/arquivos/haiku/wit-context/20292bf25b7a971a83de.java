/*
 * @test
 * @summary Verifies submit(Runnable) rejects a null task and schedules a valid task for immediate execution
 * @run main ScheduledThreadPoolExecutorWitupTest
 */
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        try {
            boolean thrown = false;
            try {
                exec.submit((Runnable) null);
            } catch (NullPointerException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("expected NullPointerException for null task");
            }

            AtomicInteger counter = new AtomicInteger();
            Future<?> f = exec.submit(() -> { counter.incrementAndGet(); });
            Object result = f.get();
            if (result != null) {
                throw new AssertionError("expected null result for Runnable task, got " + result);
            }
            if (counter.get() != 1) {
                throw new AssertionError("expected task to run once, count=" + counter.get());
            }
            if (!f.isDone()) {
                throw new AssertionError("expected future to be done after get()");
            }
        } finally {
            exec.shutdownNow();
        }
    }
}
