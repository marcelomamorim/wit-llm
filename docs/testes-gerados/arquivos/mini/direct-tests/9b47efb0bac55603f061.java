/*
 * @test
 * @summary Test ExecutorCompletionService.submit(Runnable, V) method behavior
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.*;

public class ExecutorCompletionServiceWitupTest {
    public static void main(String[] args) throws Exception {
        testSubmitReturnsFutureWithResult();
        testSubmitNullTaskThrowsNPE();
    }

    static void testSubmitReturnsFutureWithResult() throws Exception {
        Executor executor = new DirectExecutor();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);

        Runnable task = new Runnable() {
            public void run() {
                // no-op
            }
        };

        String result = "testResult";
        Future<String> future = ecs.submit(task, result);

        if (future == null) {
            throw new AssertionError("submit returned null Future");
        }

        // The task does nothing, but the Future should return the given result
        String got = future.get();
        if (!result.equals(got)) {
            throw new AssertionError("Future.get() returned " + got + ", expected " + result);
        }
    }

    static void testSubmitNullTaskThrowsNPE() {
        Executor executor = new DirectExecutor();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);

        try {
            ecs.submit(null, "irrelevant");
            throw new AssertionError("submit(null, result) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("submit(null, result) threw wrong exception: " + t);
        }
    }

    // Simple Executor that runs tasks synchronously in calling thread
    static class DirectExecutor implements Executor {
        public void execute(Runnable r) {
            r.run();
        }
    }
}
