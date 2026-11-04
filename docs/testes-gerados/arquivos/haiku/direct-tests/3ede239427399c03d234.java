/*
 * @test
 * @summary Tests Executors.unconfigurableScheduledExecutorService null handling, delegation and wrapper type
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Executors.unconfigurableScheduledExecutorService(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }

        ScheduledExecutorService inner = Executors.newScheduledThreadPool(1);
        try {
            ScheduledExecutorService wrapped =
                Executors.unconfigurableScheduledExecutorService(inner);
            check(wrapped != null, "wrapper must not be null");
            check(wrapped != inner, "wrapper must be a distinct object");
            check(!(wrapped instanceof ThreadPoolExecutor),
                  "wrapper must not expose the underlying pool type");

            Future<Integer> f = wrapped.schedule(() -> 42, 10, TimeUnit.MILLISECONDS);
            check(f.get() == 42, "scheduled task must delegate and return 42");

            check(!wrapped.isShutdown(), "wrapper must not be shut down initially");
            wrapped.shutdown();
            check(inner.isShutdown(), "shutdown must propagate to delegate");
            check(wrapped.isShutdown(), "wrapper must report shutdown");
            check(wrapped.awaitTermination(10, TimeUnit.SECONDS),
                  "wrapper must terminate after delegate terminates");
        } finally {
            inner.shutdownNow();
        }
    }
}
