/*
 * @test
 * @summary Tests for ScheduledThreadPoolExecutor.add(Runnable e)
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionException;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testAddWithNullRunnable();
        testAddWithValidRunnable();
    }

    private static void testAddWithNullRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.add(null);
            System.out.println("Test add with null Runnable: Failed (no exception)");
        } catch (NullPointerException e) {
            System.out.println("Test add with null Runnable: Passed (NullPointerException caught)");
        } catch (Exception e) {
            System.out.println("Test add with null Runnable: Failed (unexpected exception: " + e + ")");
        }
    }

    private static void testAddWithValidRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Runnable r = new Runnable() {
            public void run() {
                // do nothing
            }
        };
        try {
            boolean result = executor.add(r);
            if (result) {
                System.out.println("Test add with valid Runnable: Passed");
            } else {
                System.out.println("Test add with valid Runnable: Failed (add returned false)");
            }
        } catch (Exception e) {
            System.out.println("Test add with valid Runnable: Failed (exception: " + e + ")");
        }
    }
}
