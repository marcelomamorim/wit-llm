/*
 * @test
 * @summary Basic tests for FutureTask.get() method behavior
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskWitupTest {
    public static void main(String[] args) throws Exception {
        testGetReturnsCallableResult();
        testGetBlocksUntilDone();
        testGetThrowsExecutionExceptionOnCallableException();
    }

    static void testGetReturnsCallableResult() throws Exception {
        FutureTask<String> ft = new FutureTask<>(new Callable<String>() {
            public String call() {
                return "success";
            }
        });
        ft.run();
        String result = ft.get();
        if (!"success".equals(result)) {
            throw new AssertionError("Expected 'success' but got: " + result);
        }
    }

    static void testGetBlocksUntilDone() throws Exception {
        final FutureTask<String> ft = new FutureTask<>(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(200);
                return "done";
            }
        });
        Thread t = new Thread(ft);
        t.start();
        long start = System.currentTimeMillis();
        String result = ft.get();
        long elapsed = System.currentTimeMillis() - start;
        if (!"done".equals(result)) {
            throw new AssertionError("Expected 'done' but got: " + result);
        }
        if (elapsed < 180) { // allow some margin
            throw new AssertionError("get() returned too early, elapsed: " + elapsed);
        }
        t.join();
    }

    static void testGetThrowsExecutionExceptionOnCallableException() {
        FutureTask<String> ft = new FutureTask<>(new Callable<String>() {
            public String call() {
                throw new RuntimeException("fail");
            }
        });
        ft.run();
        try {
            ft.get();
            throw new AssertionError("Expected ExecutionException not thrown");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause == null || !"fail".equals(cause.getMessage())) {
                throw new AssertionError("Unexpected cause message: " + (cause == null ? "null" : cause.getMessage()));
            }
        } catch (InterruptedException e) {
            throw new AssertionError("Unexpected InterruptedException", e);
        }
    }
}
