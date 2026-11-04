/*
 * @test
 * @summary FutureTask.get returns results and reports ExecutionException, CancellationException and InterruptedException
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testCallableResult();
        testRunnableWithResult();
        testNullResult();
        testExecutionException();
        testCancelled();
        testGetBlocksUntilRun();
        testInterruptedBeforeGet();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCallableResult() throws Exception {
        FutureTask<String> f = new FutureTask<>(() -> "ok");
        f.run();
        check("ok".equals(f.get()), "get() should return callable result");
    }

    static void testRunnableWithResult() throws Exception {
        FutureTask<String> f = new FutureTask<>(() -> { }, "done");
        f.run();
        check("done".equals(f.get()), "get() should return the result given to Runnable constructor");
    }

    static void testNullResult() throws Exception {
        FutureTask<Object> f = new FutureTask<>(() -> null);
        f.run();
        check(f.get() == null, "get() should return null when callable returns null");
    }

    static void testExecutionException() throws Exception {
        FutureTask<Integer> f = new FutureTask<>(() -> {
            throw new IllegalStateException("boom");
        });
        f.run();
        try {
            f.get();
            throw new RuntimeException("expected ExecutionException");
        } catch (ExecutionException e) {
            check(e.getCause() instanceof IllegalStateException,
                  "cause of ExecutionException should be the thrown exception");
        }
    }

    static void testCancelled() throws Exception {
        FutureTask<String> f = new FutureTask<>(() -> "x");
        check(f.cancel(false), "cancel should succeed before run");
        try {
            f.get();
            throw new RuntimeException("expected CancellationException");
        } catch (CancellationException e) {
            // expected
        }
    }

    static void testGetBlocksUntilRun() throws Exception {
        FutureTask<String> f = new FutureTask<>(() -> "late");
        final String[] box = new String[1];
        Thread t = new Thread(() -> {
            try {
                box[0] = f.get();
            } catch (InterruptedException | ExecutionException e) {
                box[0] = "error";
            }
        });
        t.start();
        f.run();
        t.join();
        check("late".equals(box[0]), "blocked get() should return result after run()");
    }

    static void testInterruptedBeforeGet() throws Exception {
        FutureTask<String> f = new FutureTask<>(() -> "never");
        Thread.currentThread().interrupt();
        try {
            f.get();
            throw new RuntimeException("expected InterruptedException");
        } catch (InterruptedException e) {
            // expected
        } finally {
            Thread.interrupted();
        }
    }
}
