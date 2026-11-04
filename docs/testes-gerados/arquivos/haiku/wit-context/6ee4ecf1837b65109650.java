/*
 * @test
 * @summary Witup-derived checks for ThreadPoolExecutor.allowCoreThreadTimeOut
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        zeroKeepAliveRejectsTimeOutAndKeepsState();
        positiveKeepAliveAcceptsTimeOutAndToggles();
    }

    static void zeroKeepAliveRejectsTimeOutAndKeepsState() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            pool.allowCoreThreadTimeOut(false);
            if (pool.allowsCoreThreadTimeOut()) {
                throw new AssertionError("expected core timeout disabled");
            }
            boolean thrown = false;
            try {
                pool.allowCoreThreadTimeOut(true);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("expected IllegalArgumentException for zero keep alive");
            }
            if (pool.allowsCoreThreadTimeOut()) {
                throw new AssertionError("state must not change after rejected call");
            }
        } finally {
            pool.shutdownNow();
        }
    }

    static void positiveKeepAliveAcceptsTimeOutAndToggles() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 1, 1L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            pool.allowCoreThreadTimeOut(true);
            if (!pool.allowsCoreThreadTimeOut()) {
                throw new AssertionError("expected core timeout enabled");
            }
            pool.allowCoreThreadTimeOut(false);
            if (pool.allowsCoreThreadTimeOut()) {
                throw new AssertionError("expected core timeout disabled");
            }
        } finally {
            pool.shutdownNow();
        }
    }
}
