/*
 * @test
 * @summary Test AbstractExecutorService.submit(Callable) for null argument and normal execution
 * @run main AbstractExecutorServiceWitupTest
 */

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutionException;

public class AbstractExecutorServiceWitupTest {

    // Minimal concrete subclass to test AbstractExecutorService
    static class TestExecutorService extends AbstractExecutorService {

        volatile Runnable lastExecuted = null;

        @Override
        public void execute(Runnable command) {
            if (command == null) throw new NullPointerException();
            lastExecuted = command;
            // Run synchronously for test determinism
            command.run();
        }

        @Override
        public void shutdown() {
            // no-op
        }

        @Override
        public java.util.List<Runnable> shutdownNow() {
            return java.util.Collections.emptyList();
        }

        @Override
        public boolean isShutdown() {
            return false;
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

        @Override
        public boolean awaitTermination(long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        TestExecutorService executor = new TestExecutorService();

        // Test 1: submit(null) throws NullPointerException
        boolean npeThrown = false;
        try {
            executor.submit((Callable<Object>) null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("submit(null) did not throw NullPointerException");
        }

        // Test 2: submit a Callable that returns a known value
        Callable<String> callable = new Callable<String>() {
            public String call() {
                return "testValue";
            }
        };

        Future<String> future = executor.submit(callable);

        // The returned Future should be done after submit returns (because execute runs synchronously)
        if (!future.isDone()) {
            throw new AssertionError("Future should be done after submit returns");
        }

        // The result should be the expected value
        String result = future.get();
        if (!"testValue".equals(result)) {
            throw new AssertionError("Future returned unexpected result: " + result);
        }

        // The executor should have executed a RunnableFuture
        if (!(executor.lastExecuted instanceof RunnableFuture)) {
            throw new AssertionError("Executed task is not a RunnableFuture");
        }

        // Test 3: submit a Callable that throws checked exception
        Callable<String> throwingCallable = new Callable<String>() {
            public String call() throws Exception {
                throw new Exception("expected");
            }
        };

        Future<String> futureEx = executor.submit(throwingCallable);

        try {
            futureEx.get();
            throw new AssertionError("Expected ExecutionException was not thrown");
        } catch (ExecutionException ee) {
            Throwable cause = ee.getCause();
            if (cause == null || !"expected".equals(cause.getMessage())) {
                throw new AssertionError("ExecutionException cause unexpected: " + cause);
            }
        }
    }
}
