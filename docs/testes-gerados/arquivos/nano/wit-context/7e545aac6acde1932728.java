/*
 * @test
 * @summary Test for ScheduledThreadPoolExecutor.put(Runnable e)
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testPutWithNullRunnable();
        testPutWithValidRunnable();
    }

    private static void testPutWithNullRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            executor.put(null);
            System.out.println("testPutWithNullRunnable: Passed (no exception)");
        } catch (NullPointerException e) {
            System.out.println("testPutWithNullRunnable: Failed (unexpected NullPointerException)");
        } catch (UnsupportedOperationException e) {
            System.out.println("testPutWithNullRunnable: Failed (unexpected UnsupportedOperationException)");
        } catch (Exception e) {
            System.out.println("testPutWithNullRunnable: Failed (unexpected exception: " + e + ")");
        } finally {
            executor.shutdown();
        }
    }

    private static void testPutWithValidRunnable() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable executed");
            }
        };
        try {
            executor.put(r);
            // Wait briefly to allow execution
            Thread.sleep(100);
            System.out.println("testPutWithValidRunnable: Passed (no exception)");
        } catch (Exception e) {
            System.out.println("testPutWithValidRunnable: Failed (exception: " + e + ")");
        } finally {
            executor.shutdown();
        }
    }
}
