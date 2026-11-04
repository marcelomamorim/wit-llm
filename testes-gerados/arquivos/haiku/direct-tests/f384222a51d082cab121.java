/*
 * @test
 * @summary Basic behavior of ForkJoinPool.submit(Runnable): null rejection, execution of the runnable, and exception propagation through the returned future
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ForkJoinPoolWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        ForkJoinPool pool = new ForkJoinPool(2);
        try {
            // null task must be rejected with NullPointerException
            boolean npe = false;
            try {
                pool.submit((Runnable) null);
            } catch (NullPointerException expected) {
                npe = true;
            }
            check(npe, "submit(null) must throw NullPointerException");

            // a normal runnable is executed and the future completes with null result
            AtomicInteger counter = new AtomicInteger();
            ForkJoinTask<?> f = pool.submit((Runnable) () -> {
                counter.incrementAndGet();
            });
            check(f != null, "submit must return a non-null task");
            Object result = f.get();
            check(result == null, "Runnable result must be null");
            check(f.isDone(), "task must be done after get()");
            check(counter.get() == 1, "runnable must run exactly once");

            // an exception thrown by the runnable surfaces through get()
            ForkJoinTask<?> failing = pool.submit((Runnable) () -> {
                throw new IllegalStateException("boom");
            });
            boolean wrapped = false;
            try {
                failing.get();
            } catch (ExecutionException e) {
                wrapped = e.getCause() instanceof IllegalStateException;
            }
            check(wrapped, "exception from runnable must be wrapped in ExecutionException");
        } finally {
            pool.shutdown();
            check(pool.awaitTermination(10, TimeUnit.SECONDS), "pool must terminate");
        }
    }
}
