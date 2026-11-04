/*
 * @test
 * @summary Test submit method of AbstractExecutorService for correctness
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;

public class AbstractExecutorServiceWitupTest {

    public static void main(String[] args) {
        testSubmitWithResult();
        testSubmitWithNullTask();
    }

    static void testSubmitWithResult() {
        Executor executor = Executors.newSingleThreadExecutor();
        AbstractExecutorService executorService = (AbstractExecutorService) executor;
        Runnable task = new Runnable() {
            public void run() {
                // do nothing
            }
        };
        String resultValue = "testResult";
        Future<String> future = executorService.submit(task, resultValue);
        if (future == null) {
            throw new AssertionError("Future should not be null");
        }
        try {
            String result = future.get();
            if (!"testResult".equals(result)) {
                throw new AssertionError("Expected 'testResult', got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception while getting future result", e);
        }
        executor.shutdown();
    }

    static void testSubmitWithNullTask() {
        Executor executor = Executors.newSingleThreadExecutor();
        AbstractExecutorService executorService = (AbstractExecutorService) executor;
        try {
            executorService.submit(null, "value");
            throw new AssertionError("Expected NullPointerException for null task");
        } catch (NullPointerException e) {
            // expected
        } finally {
            executor.shutdown();
        }
    }
}
