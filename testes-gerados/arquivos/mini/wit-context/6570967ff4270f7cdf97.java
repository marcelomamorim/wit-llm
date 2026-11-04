/*
 * @test
 * @summary Test ExecutorCompletionService.submit(Callable) for null argument and normal execution
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.*;

public class ExecutorCompletionServiceWitupTest {
    public static void main(String[] args) throws Exception {
        testSubmitNullTaskThrowsNPE();
        testSubmitValidTaskExecutesAndReturnsFuture();
    }

    static void testSubmitNullTaskThrowsNPE() {
        Executor executor = Runnable::run;
        ExecutorCompletionService<Object> ecs = new ExecutorCompletionService<>(executor);
        try {
            ecs.submit(null);
            throw new AssertionError("Expected NullPointerException for null task");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testSubmitValidTaskExecutesAndReturnsFuture() throws Exception {
        Executor executor = Runnable::run;
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);

        Callable<String> task = new Callable<String>() {
            public String call() {
                return "test";
            }
        };

        Future<String> future = ecs.submit(task);
        if (future == null) {
            throw new AssertionError("submit returned null Future");
        }

        // Since executor runs tasks synchronously, future should be done
        if (!future.isDone()) {
            throw new AssertionError("Future not done after submit");
        }

        String result = future.get();
        if (!"test".equals(result)) {
            throw new AssertionError("Unexpected result from Future.get(): " + result);
        }

        // Also test that take() returns the completed future
        Future<String> taken = ecs.take();
        if (taken != future) {
            throw new AssertionError("take() did not return the submitted future");
        }
    }
}
