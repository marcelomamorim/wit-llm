/* @test    @summary Test ExecutorCompletionService.submit with non-null Callable    @run main ExecutorCompletionServiceWitupTest */

import java.util.concurrent.*;

public class ExecutorCompletionServiceWitupTest {

    public static void main(String[] args) throws Exception {
        testSubmitWithValidCallable();
        testSubmitWithNullCallable();
    }

    static void testSubmitWithValidCallable() throws Exception {
        Executor executor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);
        Callable<String> task = new Callable<String>() {
            public String call() {
                return "result";
            }
        };
        Future<String> future = ecs.submit(task);
        String result = future.get();
        assert "result".equals(result) : "Expected 'result', got " + result;
        ((ExecutorService) executor).shutdown();
    }

    static void testSubmitWithNullCallable() {
        Executor executor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);
        try {
            ecs.submit((Callable<String>) null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        } finally {
            ((ExecutorService) executor).shutdown();
        }
    }
}
