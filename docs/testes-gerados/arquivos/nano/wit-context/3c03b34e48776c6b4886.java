/*
 * @test
 * @summary Test execute method of ScheduledThreadPoolExecutor for null arguments.
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testExecuteWithNullRunnable();
    }

    public static void testExecuteWithNullRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.execute(null);
            System.out.println("Test failed: NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Test passed: NullPointerException was thrown as expected.");
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception: " + e);
        }
    }
}
