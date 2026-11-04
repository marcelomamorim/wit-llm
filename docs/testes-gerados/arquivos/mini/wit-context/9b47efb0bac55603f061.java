/*
 * @test
 * @summary Test ExecutorCompletionService.submit(Runnable, V) for null task and normal execution
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.*;

public class ExecutorCompletionServiceWitupTest {
    public static void main(String[] args) throws Exception {
        testSubmitNullTaskThrowsNPE();
        testSubmitRunnableExecutesAndReturnsFuture();
    }

    private static void testSubmitNullTaskThrowsNPE() {
        Executor executor = Runnable::run;
        ExecutorCompletionService<Object> ecs = new ExecutorCompletionService<>(executor);
        try {
            ecs.submit(null, "result");
            throw new AssertionError("Expected NullPointerException for null task");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSubmitRunnableExecutesAndReturnsFuture() throws Exception {
        final boolean[] ran = {false};
        Runnable task = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };
        Executor executor = Runnable::run;
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);
        Future<String> future = ecs.submit(task, "done");
        // The task should have run synchronously
        if (!ran[0]) {
            throw new AssertionError("Runnable task was not executed");
        }
        // The future should be done and return the given result
        if (!future.isDone()) {
            throw new AssertionError("Future is not done after submit");
        }
        String result = future.get();
        if (!"done".equals(result)) {
            throw new AssertionError("Future.get() returned unexpected result: " + result);
        }
    }
}
