/*
 * @test
 * @summary Verifies peek() of the ScheduledThreadPoolExecutor work queue through the public getQueue() accessor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
        try {
            BlockingQueue<Runnable> queue = stpe.getQueue();

            check(queue.peek() == null, "peek() on empty queue must return null");

            Runnable task = () -> { };
            ScheduledFuture<?> late = stpe.schedule(task, 1, TimeUnit.HOURS);
            check(queue.peek() == late, "peek() must return the only queued task");

            ScheduledFuture<?> early = stpe.schedule(task, 2, TimeUnit.HOURS);
            check(queue.peek() == late, "peek() must return the task with the earliest delay");

            ScheduledFuture<?> earliest = stpe.schedule(task, 30, TimeUnit.MINUTES);
            check(queue.peek() == earliest, "peek() must return the newly scheduled earliest task");

            check(queue.peek() == earliest, "peek() must be idempotent");
            check(queue.size() == 3, "peek() must not remove elements, size expected 3 but was " + queue.size());

            earliest.cancel(false);
            check(queue.peek() == earliest, "cancelled task remains at head by default (removeOnCancel disabled)");
            check(queue.size() == 3, "cancelled task must not be removed by default");
        } finally {
            stpe.shutdownNow();
        }
    }
}
