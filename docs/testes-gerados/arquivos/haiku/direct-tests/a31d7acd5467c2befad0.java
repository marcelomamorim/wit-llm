/*
 * @test
 * @summary Verifies ThreadPoolExecutor.setKeepAliveTime argument validation, unit conversion and state preservation on rejection
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        try {
            e.setKeepAliveTime(5, TimeUnit.SECONDS);
            check(e.getKeepAliveTime(TimeUnit.SECONDS) == 5, "5 seconds not stored");

            e.setKeepAliveTime(1500, TimeUnit.MILLISECONDS);
            check(e.getKeepAliveTime(TimeUnit.MILLISECONDS) == 1500, "1500 ms not stored");

            try {
                e.setKeepAliveTime(-1, TimeUnit.SECONDS);
                throw new RuntimeException("negative time accepted");
            } catch (IllegalArgumentException expected) {
            }
            check(e.getKeepAliveTime(TimeUnit.MILLISECONDS) == 1500,
                  "value changed after rejected negative time");

            e.setKeepAliveTime(0, TimeUnit.SECONDS);
            check(e.getKeepAliveTime(TimeUnit.SECONDS) == 0,
                  "zero keep alive not accepted when core timeout disabled");

            e.setKeepAliveTime(2, TimeUnit.SECONDS);
            e.allowCoreThreadTimeOut(true);
            try {
                e.setKeepAliveTime(0, TimeUnit.SECONDS);
                throw new RuntimeException("zero accepted while core timeout allowed");
            } catch (IllegalArgumentException expected) {
            }
            check(e.getKeepAliveTime(TimeUnit.SECONDS) == 2,
                  "value changed after rejected zero time");

            e.setKeepAliveTime(3, TimeUnit.SECONDS);
            check(e.getKeepAliveTime(TimeUnit.SECONDS) == 3,
                  "nonzero value not accepted while core timeout allowed");
        } finally {
            e.shutdownNow();
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
