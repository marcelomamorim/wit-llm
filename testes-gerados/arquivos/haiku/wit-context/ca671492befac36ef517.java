/*
 * @test
 * @summary Witup-derived tests for FutureTask.get() result and exception paths
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testNormalResultReturned();
        testNullResultReturned();
        testRunnableWithResultReturnsGivenValue();
        testExceptionalCompletionThrowsExecutionException();
        testCancelledBeforeRunThrowsCancellationException();
        testRunAfterCancelDoesNotChangeCancellation();
    }

    static void testNormalResultReturned() throws Exception {
        Callable<Integer> c = () -> 42;
        FutureTask<Integer> task = new FutureTask<Integer>(c);
        task.run();
        Integer r = task.get();
        if (r.intValue() != 42) {
            throw new RuntimeException("Expected 42 but got " + r);
        }
    }

    static void testNullResultReturned() throws Exception {
        Callable<String> c = () -> null;
        FutureTask<String> task = new FutureTask<String>(c);
        task.run();
        String r = task.get();
        if (r != null) {
            throw new RuntimeException("Expected null but got " + r);
        }
    }

    static void testRunnableWithResultReturnsGivenValue() throws Exception {
        Runnable run = () -> { };
        FutureTask<String> task = new FutureTask<String>(run, "done");
        task.run();
        String r = task.get();
        if (!"done".equals(r)) {
            throw new RuntimeException("Expected 'done' but got " + r);
        }
    }

    static void testExceptionalCompletionThrowsExecutionException() throws Exception {
        final IllegalStateException cause = new IllegalStateException("boom");
        Callable<Integer> c = () -> { throw cause; };
        FutureTask<Integer> task = new FutureTask<Integer>(c);
        task.run();
        try {
            task.get();
        } catch (ExecutionException e) {
            if (e.getCause() != cause) {
                throw new RuntimeException("Unexpected cause: " + e.getCause());
            }
            return;
        }
        throw new RuntimeException("ExecutionException not thrown");
    }

    static void testCancelledBeforeRunThrowsCancellationException() throws Exception {
        FutureTask<Integer> task = new FutureTask<Integer>(() -> 1);
        if (!task.cancel(false)) {
            throw new RuntimeException("cancel(false) should succeed on new task");
        }
        try {
            task.get();
        } catch (CancellationException e) {
            return;
        }
        throw new RuntimeException("CancellationException not thrown");
    }

    static void testRunAfterCancelDoesNotChangeCancellation() throws Exception {
        FutureTask<Integer> task = new FutureTask<Integer>(() -> 1);
        task.cancel(false);
        task.run();
        try {
            task.get();
        } catch (CancellationException e) {
            return;
        }
        throw new RuntimeException("CancellationException not thrown after run()");
    }
}
