/*
 * @test
 * @summary Verify that the ScheduledThreadPoolExecutor work queue size reflects scheduled and cancelled tasks
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        emptyQueueHasZeroSize();
        queueSizeReflectsScheduledTasks();
        cancelWithRemoveOnCancelPolicyReducesSize();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void emptyQueueHasZeroSize() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            check(ex.getQueue().size() == 0, "new executor queue size must be 0");
        } finally {
            ex.shutdownNow();
        }
    }

    static void queueSizeReflectsScheduledTasks() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            ex.schedule(() -> { }, 2, TimeUnit.HOURS);
            ex.schedule(() -> { }, 3, TimeUnit.HOURS);
            check(ex.getQueue().size() == 3,
                  "queue size must be 3 after scheduling 3 delayed tasks, was "
                  + ex.getQueue().size());
        } finally {
            ex.shutdownNow();
        }
    }

    static void cancelWithRemoveOnCancelPolicyReducesSize() {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(1);
        try {
            ex.setRemoveOnCancelPolicy(true);
            ScheduledFuture<?> f1 = ex.schedule(() -> { }, 1, TimeUnit.HOURS);
            ex.schedule(() -> { }, 2, TimeUnit.HOURS);
            check(ex.getQueue().size() == 2, "queue size must be 2 before cancellation");
            f1.cancel(false);
            check(ex.getQueue().size() == 1,
                  "queue size must be 1 after cancelling one task with remove-on-cancel, was "
                  + ex.getQueue().size());
        } finally {
            ex.shutdownNow();
        }
    }
}
