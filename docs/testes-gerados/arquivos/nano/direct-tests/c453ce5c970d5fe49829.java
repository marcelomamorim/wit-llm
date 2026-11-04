/*
 * @test
 * @summary Test the toArray() method of ScheduledThreadPoolExecutor for correct array output.
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testToArrayEmpty();
        testToArrayWithTasks();
    }

    static void testToArrayEmpty() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        Object[] array = executor.toArray();
        assert array != null : "Array should not be null";
        assert array.length == 0 : "Array length should be 0 for empty queue";
        executor.shutdown();
    }

    static void testToArrayWithTasks() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        Runnable task1 = () -> {};
        Runnable task2 = () -> {};
        executor.execute(task1);
        executor.execute(task2);
        try {
            Thread.sleep(50); // Allow tasks to be queued
        } catch (InterruptedException e) {
            // ignore
        }
        Object[] array = executor.toArray();
        assert array != null : "Array should not be null";
        assert array.length >= 2 : "Array should contain at least 2 tasks";
        boolean containsTask1 = false;
        boolean containsTask2 = false;
        for (Object obj : array) {
            if (obj == task1) containsTask1 = true;
            if (obj == task2) containsTask2 = true;
        }
        assert containsTask1 : "Array should contain task1";
        assert containsTask2 : "Array should contain task2";
        executor.shutdown();
    }
}
