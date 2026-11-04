/*
 * @test
 * @summary Verifies that ThreadPoolExecutor.setRejectedExecutionHandler rejects null with NullPointerException and keeps the previous handler unchanged
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            boolean npe = false;
            try {
                executor.setRejectedExecutionHandler(null);
            } catch (NullPointerException e) {
                npe = true;
            }
            if (!npe) {
                throw new AssertionError("expected NullPointerException for null handler");
            }

            RejectedExecutionHandler original = executor.getRejectedExecutionHandler();
            if (original == null) {
                throw new AssertionError("default handler should not be null");
            }

            RejectedExecutionHandler custom = new RejectedExecutionHandler() {
                public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                }
            };
            executor.setRejectedExecutionHandler(custom);
            if (executor.getRejectedExecutionHandler() != custom) {
                throw new AssertionError("handler was not updated to the custom handler");
            }

            npe = false;
            try {
                executor.setRejectedExecutionHandler(null);
            } catch (NullPointerException e) {
                npe = true;
            }
            if (!npe) {
                throw new AssertionError("expected NullPointerException for null handler after valid set");
            }
            if (executor.getRejectedExecutionHandler() != custom) {
                throw new AssertionError("handler changed after rejected null assignment");
            }
        } finally {
            executor.shutdownNow();
        }
    }
}
