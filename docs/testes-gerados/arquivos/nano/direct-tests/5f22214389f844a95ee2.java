/* @test    @summary Test setRejectedExecutionHandler method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSetRejectedExecutionHandlerWithValidHandler();
        testSetRejectedExecutionHandlerWithNull();
    }

    private static void testSetRejectedExecutionHandlerWithValidHandler() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, null);
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                // no-op
            }
        };
        executor.setRejectedExecutionHandler(handler);
        // No exception thrown, test passes
    }

    private static void testSetRejectedExecutionHandlerWithNull() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, null);
        try {
            executor.setRejectedExecutionHandler(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
