/*
 * @test
 * @summary Verifies ScheduledThreadPoolExecutor queue isEmpty() reflects scheduled and cancelled tasks
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyOnNewExecutor();
        testNotEmptyAfterScheduling();
        testEmptyAfterCancelWithRemoveOnCancelPolicy();
        testNotEmptyAfterCancelWithoutRemoveOnCancelPolicy();
    }

    static void testEmptyOnNewExecutor() {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> q = stpe.getQueue();
            check(q.isEmpty(), "new executor queue should be empty");
        } finally {
            stpe.shutdownNow();
        }
    }

    static void testNotEmptyAfterScheduling() {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            stpe.schedule(() -> { }, 1, TimeUnit.HOURS);
            check(!stpe.getQueue().isEmpty(), "queue should not be empty after scheduling");
        } finally {
            stpe.shutdownNow();
        }
    }

    static void testEmptyAfterCancelWithRemoveOnCancelPolicy() {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            stpe.setRemoveOnCancelPolicy(true);
            ScheduledFuture<?> f = stpe.schedule(() -> { }, 1, TimeUnit.HOURS);
            check(!stpe.getQueue().isEmpty(), "queue should not be empty after scheduling");
            check(f.cancel(false), "cancel should succeed");
            check(stpe.getQueue().isEmpty(), "queue should be empty after cancel with remove-on-cancel policy");
        } finally {
            stpe.shutdownNow();
        }
    }

    static void testNotEmptyAfterCancelWithoutRemoveOnCancelPolicy() {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            check(!stpe.getRemoveOnCancelPolicy(), "default remove-on-cancel policy should be false");
            ScheduledFuture<?> f = stpe.schedule(() -> { }, 1, TimeUnit.HOURS);
            check(f.cancel(false), "cancel should succeed");
            check(!stpe.getQueue().isEmpty(), "cancelled task should remain queued without remove-on-cancel policy");
        } finally {
            stpe.shutdownNow();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
