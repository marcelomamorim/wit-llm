/*
 * @test
 * @summary setMaximumPoolSize rejects invalid bounds and updates the maximum pool size
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        ThreadPoolExecutor e = new ThreadPoolExecutor(2, 4, 1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            expectIllegalArgument(e, 0);
            expectIllegalArgument(e, -1);
            expectIllegalArgument(e, 1);
            check(e.getMaximumPoolSize() == 4, "maximum changed after rejected value");

            e.setMaximumPoolSize(2);
            check(e.getMaximumPoolSize() == 2, "maximum equal to core should be accepted");

            e.setMaximumPoolSize(8);
            check(e.getMaximumPoolSize() == 8, "maximum should be updated to 8");
        } finally {
            e.shutdownNow();
        }
    }

    static void expectIllegalArgument(ThreadPoolExecutor e, int value) {
        try {
            e.setMaximumPoolSize(value);
        } catch (IllegalArgumentException ex) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for " + value);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
