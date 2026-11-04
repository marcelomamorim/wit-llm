/*
 * @test
 * @summary Test for submit method in ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSubmitWithNullTask();
        testSubmitWithValidTask();
    }

    private static void testSubmitWithNullTask() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.submit(null);
            System.out.println("testSubmitWithNullTask: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testSubmitWithNullTask: Passed");
        } catch (Exception e) {
            System.out.println("testSubmitWithNullTask: Failed - Unexpected exception: " + e);
        } finally {
            executor.shutdown();
        }
    }

    private static void testSubmitWithValidTask() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Task executed");
            }
        };
        try {
            Future<?> future = executor.submit(task);
            future.get(); // wait for completion
            System.out.println("testSubmitWithValidTask: Passed");
        } catch (Exception e) {
            System.out.println("testSubmitWithValidTask: Failed - Exception: " + e);
        } finally {
            executor.shutdown();
        }
    }
}
