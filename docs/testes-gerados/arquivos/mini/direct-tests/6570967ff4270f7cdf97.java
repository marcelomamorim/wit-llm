/*
 * @test
 * @summary Test ExecutorCompletionService.submit(Callable) behavior
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.*;

public class ExecutorCompletionServiceWitupTest {
    public static void main(String[] args) throws Exception {
        testSubmitReturnsFutureWithCorrectResult();
        testSubmitNullCallableThrowsNPE();
    }

    static void testSubmitReturnsFutureWithCorrectResult() throws Exception {
        Executor executor = new DirectExecutor();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);

        Callable<String> task = new Callable<String>() {
            public String call() {
                return "test-result";
            }
        };

        Future<String> future = ecs.submit(task);
        if (future == null) throw new AssertionError("submit returned null Future");

        String result = future.get();
        if (!"test-result".equals(result)) {
            throw new AssertionError("Expected 'test-result' but got: " + result);
        }
    }

    static void testSubmitNullCallableThrowsNPE() {
        Executor executor = new DirectExecutor();
        ExecutorCompletionService<Object> ecs = new ExecutorCompletionService<>(executor);

        try {
            ecs.submit(null);
            throw new AssertionError("Expected NullPointerException for null Callable");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new AssertionError("Expected NullPointerException but got " + t);
        }
    }

    // Executor that runs tasks synchronously in the calling thread
    static class DirectExecutor implements Executor {
        public void execute(Runnable r) {
            r.run();
        }
    }
}
