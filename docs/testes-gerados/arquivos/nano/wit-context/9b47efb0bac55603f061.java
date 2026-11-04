/*
 * @test
 * @summary Test for ExecutorCompletionService.submit(Runnable, V) method.
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;

public class ExecutorCompletionServiceWitupTest {

    public static void main(String[] args) {
        testSubmitWithNullTask();
        testSubmitWithValidTask();
    }

    private static void testSubmitWithNullTask() {
        Executor executor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);
        try {
            ecs.submit(null, "result");
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Esperado
        } finally {
            if (executor instanceof java.util.concurrent.ExecutorService) {
                ((java.util.concurrent.ExecutorService) executor).shutdown();
            }
        }
    }

    private static void testSubmitWithValidTask() {
        Executor executor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);
        Runnable task = new Runnable() {
            public void run() {
                // Tarefa vazia
            }
        };
        Future<String> future = ecs.submit(task, "done");
        try {
            String result = future.get();
            if (!"done".equals(result)) {
                throw new AssertionError("Unexpected result: " + result);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            if (executor instanceof java.util.concurrent.ExecutorService) {
                ((java.util.concurrent.ExecutorService) executor).shutdown();
            }
        }
    }
}
