/*
 * @test
 * @summary Basic observable behavior of ScheduledThreadPoolExecutor.submit(Callable)
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testSubmitReturnsValue();
        testSubmitPropagatesException();
        testSubmitAfterShutdownIsRejected();
    }

    static void testSubmitReturnsValue() throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            Callable<String> c = () -> "ok";
            Future<String> f = ex.submit(c);
            if (f == null) {
                throw new RuntimeException("submit returned null Future");
            }
            String result = f.get(10, TimeUnit.SECONDS);
            if (!"ok".equals(result)) {
                throw new RuntimeException("unexpected result: " + result);
            }
            if (!f.isDone()) {
                throw new RuntimeException("future not done after get");
            }
        } finally {
            ex.shutdownNow();
        }
    }

    static void testSubmitPropagatesException() throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            Callable<String> c = () -> {
                throw new IllegalStateException("boom");
            };
            Future<String> f = ex.submit(c);
            try {
                f.get(10, TimeUnit.SECONDS);
                throw new RuntimeException("expected ExecutionException");
            } catch (ExecutionException e) {
                if (!(e.getCause() instanceof IllegalStateException)) {
                    throw new RuntimeException("unexpected cause: " + e.getCause());
                }
            }
        } finally {
            ex.shutdownNow();
        }
    }

    static void testSubmitAfterShutdownIsRejected() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        ex.shutdown();
        try {
            Callable<String> c = () -> "late";
            ex.submit(c);
            throw new RuntimeException("expected RejectedExecutionException");
        } catch (RejectedExecutionException expected) {
            // expected
        }
    }
}
