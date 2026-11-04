/* @test    @summary Tests for ExecutorCompletionService.submit(Callable) method    @run main ExecutorCompletionServiceWitupTest */

import java.util.concurrent.*;

public class ExecutorCompletionServiceWitupTest {

    public static void main(String[] args) throws Exception {
        testSubmitWithNullTask();
        testSubmitWithValidTask();
    }

    private static void testSubmitWithNullTask() {
        Executor executor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<Object> ecs = new ExecutorCompletionService<>(executor);
        try {
            ecs.submit((Callable<Object>) null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } finally {
            if (executor instanceof ExecutorService) {
                ((ExecutorService) executor).shutdown();
            }
        }
    }

    private static void testSubmitWithValidTask() throws Exception {
        Executor executor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);
        Callable<String> task = () -> "test";
        Future<String> future = ecs.submit(task);
        String result = null;
        try {
            result = future.get();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        } finally {
            if (executor instanceof ExecutorService) {
                ((ExecutorService) executor).shutdown();
            }
        }
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', got: " + result);
        }
    }
}
