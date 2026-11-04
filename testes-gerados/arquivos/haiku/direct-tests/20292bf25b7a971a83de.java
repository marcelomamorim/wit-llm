/*
 * @test
 * @summary ScheduledThreadPoolExecutor.submit(Runnable) runs the task with zero delay and returns a completed Future with null result
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            AtomicBoolean ran = new AtomicBoolean(false);
            Runnable r = () -> ran.set(true);
            Future<?> f = ex.submit(r);
            check(f != null, "submit must return a non-null Future");
            Object result = f.get();
            check(result == null, "Future of a Runnable must yield null");
            check(ran.get(), "submitted Runnable must have been executed");
            check(f.isDone(), "Future must be done after get()");
            check(!f.isCancelled(), "Future must not be cancelled");
        } finally {
            ex.shutdownNow();
        }

        ScheduledThreadPoolExecutor ex2 = new ScheduledThreadPoolExecutor(2);
        try {
            AtomicBoolean a = new AtomicBoolean(false);
            AtomicBoolean b = new AtomicBoolean(false);
            Future<?> fa = ex2.submit((Runnable) () -> a.set(true));
            Future<?> fb = ex2.submit((Runnable) () -> b.set(true));
            check(fa != fb, "each submit must return a distinct Future");
            check(fa.get() == null, "first Future must yield null");
            check(fb.get() == null, "second Future must yield null");
            check(a.get() && b.get(), "both submitted tasks must run");
        } finally {
            ex2.shutdownNow();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
