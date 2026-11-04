/*
 * @test
 * @summary Verifies observable emptiness of the ScheduledThreadPoolExecutor work queue through getQueue()
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            check(stpe.getQueue().isEmpty(), "new executor queue should be empty");
            check(stpe.getQueue().size() == 0, "new executor queue size should be 0");

            stpe.schedule(() -> { }, 1, TimeUnit.HOURS);
            check(!stpe.getQueue().isEmpty(), "queue should not be empty after scheduling a delayed task");
            check(stpe.getQueue().size() == 1, "queue size should be 1 after one scheduled task");

            List<Runnable> drained = stpe.shutdownNow();
            check(drained.size() == 1, "shutdownNow should return the one pending task");
            check(stpe.getQueue().isEmpty(), "queue should be empty after shutdownNow drains it");
        } finally {
            stpe.shutdownNow();
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
