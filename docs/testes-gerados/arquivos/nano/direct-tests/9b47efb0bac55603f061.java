/*
 * @test
 * @summary Tests the submit method of ExecutorCompletionService for correct behavior.
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.*;

public class ExecutorCompletionServiceWitupTest {

    public static void main(String[] args) throws Exception {
        testSubmitWithNullTask();
        testSubmitWithValidRunnable();
    }

    private static void testSubmitWithNullTask() {
        Executor executor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);
        try {
            ecs.submit(null, "result");
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        } finally {
            if (executor instanceof ExecutorService) {
                ((ExecutorService) executor).shutdown();
            }
        }
    }

    private static void testSubmitWithValidRunnable() throws Exception {
        Executor executor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);
        Runnable task = () -> {};
        String resultValue = "done";
        Future<String> future = ecs.submit(task, resultValue);
        if (future == null) {
            throw new AssertionError("submit returned null");
        }
        // Wait for task completion
        String result = ecs.take().get();
        if (!"done".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
        if (future.isDone() == false) {
            throw new AssertionError("Future not marked as done");
        }
        if (executor instanceof ExecutorService) {
            ((ExecutorService) executor).shutdown();
        }
    }
}
