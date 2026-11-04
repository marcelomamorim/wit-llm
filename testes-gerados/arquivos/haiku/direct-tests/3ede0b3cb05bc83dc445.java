/*
 * @test
 * @summary Verifies AbstractExecutorService.submit(Callable) wraps the task, delegates to execute, and rejects null
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AbstractExecutorServiceWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static class DirectExecutor extends AbstractExecutorService {
        int executeCount = 0;

        public void execute(Runnable command) {
            executeCount++;
            command.run();
        }

        public void shutdown() {
        }

        public List<Runnable> shutdownNow() {
            return new ArrayList<Runnable>();
        }

        public boolean isShutdown() {
            return false;
        }

        public boolean isTerminated() {
            return false;
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) {
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        DirectExecutor ex = new DirectExecutor();

        Callable<String> ok = new Callable<String>() {
            public String call() {
                return "ok";
            }
        };
        Future<String> f = ex.submit(ok);
        check(ex.executeCount == 1, "submit must delegate to execute exactly once");
        check(f != null, "submit must return a non-null Future");
        check(f.isDone(), "future should be done when execute runs the task synchronously");
        check(!f.isCancelled(), "future should not be cancelled");
        check("ok".equals(f.get()), "future must yield the callable's result");

        Callable<Integer> throwing = new Callable<Integer>() {
            public Integer call() throws Exception {
                throw new IllegalStateException("boom");
            }
        };
        Future<Integer> f2 = ex.submit(throwing);
        check(ex.executeCount == 2, "second submit must also delegate to execute");
        try {
            f2.get();
            check(false, "get() must throw ExecutionException for a failing callable");
        } catch (ExecutionException e) {
            check(e.getCause() instanceof IllegalStateException,
                  "ExecutionException cause must be the callable's exception");
        }

        try {
            ex.submit((Callable<String>) null);
            check(false, "submit(null) must throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        check(ex.executeCount == 2, "submit(null) must not call execute");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
