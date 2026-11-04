/*
 * @test
 * @summary Tests for FutureTask.get() method behavior and exception paths
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutionException;

public class FutureTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testGetReturnsResultWhenStateIsCompleted();
        testGetThrowsExecutionExceptionOnExceptionState();
        testGetThrowsCancellationExceptionOnCancelledState();
    }

    private static void testGetReturnsResultWhenStateIsCompleted() throws Exception {
        // Setup: create a FutureTask that is already completed with a result
        FutureTask<String> task = new FutureTask<>(() -> "done");
        // Manually set state to completed (simulate)
        // Since state is package-private, we cannot set directly; instead, run the task
        task.run();
        // Wait for completion
        String result = task.get();
        assert "done".equals(result) : "Expected result 'done', got: " + result;
    }

    private static void testGetThrowsExecutionExceptionOnExceptionState() {
        // Setup: create a FutureTask that throws an exception
        FutureTask<String> task = new FutureTask<>(() -> {
            throw new RuntimeException("test exception");
        });
        task.run();
        try {
            task.get();
            assert false : "Expected ExecutionException";
        } catch (ExecutionException e) {
            assert e.getCause() instanceof RuntimeException : "Cause should be RuntimeException";
            assert "test exception".equals(e.getCause().getMessage()) : "Unexpected exception message";
        } catch (InterruptedException e) {
            assert false : "Did not expect InterruptedException";
        }
    }

    private static void testGetThrowsCancellationExceptionOnCancelledState() {
        // Setup: create a FutureTask and cancel it
        FutureTask<String> task = new FutureTask<>(() -> "should not run");
        task.cancel(true);
        try {
            task.get();
            assert false : "Expected CancellationException";
        } catch (InterruptedException e) {
            // get() does not throw CancellationException, but InterruptedException
            // So, test for InterruptedException if needed
            // For this test, we assume get() may throw InterruptedException
        } catch (ExecutionException e) {
            assert false : "Did not expect ExecutionException";
        } catch (java.util.concurrent.CancellationException e) {
            // Expected
        }
    }
}
