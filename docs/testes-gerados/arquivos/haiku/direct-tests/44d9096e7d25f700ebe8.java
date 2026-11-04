/*
 * @test
 * @summary Verifies the observable queue size of ScheduledThreadPoolExecutor through getQueue() with default and remove-on-cancel policies
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyQueueSize();
        testDefaultPolicyRetainsCancelledTask();
        testRemoveOnCancelPolicyDecrementsSize();
    }

    static void testEmptyQueueSize() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            check(ex.getQueue().size() == 0, "new executor queue size must be 0");
        } finally {
            ex.shutdownNow();
        }
    }

    static void testDefaultPolicyRetainsCancelledTask() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            ScheduledFuture<?> f1 = ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            check(ex.getQueue().size() == 3, "three scheduled tasks must yield size 3");
            f1.cancel(false);
            check(ex.getQueue().size() == 3, "default policy must keep cancelled task in queue");
        } finally {
            ex.shutdownNow();
        }
        check(ex.getQueue().size() == 0, "shutdownNow must drain the queue");
    }

    static void testRemoveOnCancelPolicyDecrementsSize() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            ex.setRemoveOnCancelPolicy(true);
            ScheduledFuture<?> f1 = ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            check(ex.getQueue().size() == 2, "two scheduled tasks must yield size 2");
            f1.cancel(false);
            check(ex.getQueue().size() == 1, "remove-on-cancel policy must remove cancelled task");
        } finally {
            ex.shutdownNow();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
