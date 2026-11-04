/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor.remove(Object) method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class ScheduledThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        // Test 1: remove on a task that was never scheduled returns false
        Runnable task1 = new Runnable() {
            public void run() {}
        };
        boolean removed = executor.remove(task1);
        if (removed) {
            throw new RuntimeException("remove returned true for a task never scheduled");
        }

        // Test 2: schedule a task, then remove it before execution
        Runnable task2 = new Runnable() {
            public void run() {
                throw new RuntimeException("Task2 should not run if removed");
            }
        };
        ScheduledFuture<?> future2 = executor.schedule(task2, 100, TimeUnit.MILLISECONDS);
        boolean removed2 = executor.remove(task2);
        if (!removed2) {
            throw new RuntimeException("remove returned false for a scheduled task");
        }

        // Wait to ensure task2 would have run if not removed
        Thread.sleep(200);
        if (!future2.isCancelled() && !future2.isDone()) {
            throw new RuntimeException("Task2 was not removed properly and may have run");
        }

        // Test 3: schedule a callable task, remove it, verify removal
        Callable<String> callable = new Callable<String>() {
            public String call() {
                throw new RuntimeException("Callable should not run if removed");
            }
        };
        ScheduledFuture<String> future3 = executor.schedule(callable, 100, TimeUnit.MILLISECONDS);
        boolean removed3 = executor.remove(callable);
        if (removed3) {
            // The remove method expects the exact object in the queue, so removing callable directly should fail
            // because the queue contains a RunnableScheduledFuture wrapping the callable, not the callable itself
            // So removal should be false here
            throw new RuntimeException("remove returned true for callable object which is not directly in queue");
        }

        // Instead remove the ScheduledFuture itself
        boolean removedFuture3 = executor.remove(future3);
        if (!removedFuture3) {
            throw new RuntimeException("remove returned false for ScheduledFuture of callable task");
        }

        // Wait to ensure callable task would have run if not removed
        Thread.sleep(200);
        if (!future3.isCancelled() && !future3.isDone()) {
            throw new RuntimeException("Callable task was not removed properly and may have run");
        }

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new RuntimeException("Executor did not terminate in time");
        }
    }
}
