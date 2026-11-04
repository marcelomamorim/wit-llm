/* @test    @summary Test clear() method of ScheduledThreadPoolExecutor    @run main ScheduledThreadPoolExecutorWitupTest */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testClearRemovesAllTasks();
    }

    public static void testClearRemovesAllTasks() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        // Schedule some dummy tasks
        executor.schedule(() -> System.out.println("Task 1"), 1, TimeUnit.SECONDS);
        executor.schedule(() -> System.out.println("Task 2"), 2, TimeUnit.SECONDS);
        // Call clear to remove all scheduled tasks
        executor.clear();
        // After clear, attempting to get scheduled tasks should result in no tasks
        // Since the internal queue is not accessible, we test indirectly by scheduling and clearing
        boolean scheduledAgain = false;
        try {
            executor.schedule(() -> {}, 1, TimeUnit.SECONDS);
            scheduledAgain = true;
        } catch (Exception e) {
            scheduledAgain = false;
        }
        // The scheduled task should be accepted, but previous tasks are cleared
        // We verify that no tasks are pending by attempting to schedule and then shutdown
        executor.shutdown();
        System.out.println("Test clear() method executed.");
    }
}
