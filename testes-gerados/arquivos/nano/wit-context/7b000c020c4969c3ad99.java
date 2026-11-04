/* @test    @summary Test for ForkJoinTask.adapt method handling null Runnable    @run main ForkJoinTaskWitupTest */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {

    public static void main(String[] args) {
        testAdaptWithNullRunnable();
        testAdaptWithValidRunnable();
    }

    public static void testAdaptWithNullRunnable() {
        try {
            ForkJoinTask<?> task = ForkJoinTask.adapt(null, "result");
            if (task == null) {
                System.out.println("Test passed: adapt returned null for null Runnable");
            } else {
                System.out.println("Test failed: adapt did not return null for null Runnable");
            }
        } catch (NullPointerException e) {
            System.out.println("Test passed: adapt threw NullPointerException for null Runnable");
        } catch (Exception e) {
            System.out.println("Test failed: adapt threw unexpected exception: " + e);
        }
    }

    public static void testAdaptWithValidRunnable() {
        Runnable r = new Runnable() {
            public void run() {
                // do nothing
            }
        };
        try {
            ForkJoinTask<?> task = ForkJoinTask.adapt(r, "result");
            if (task != null) {
                System.out.println("Test passed: adapt returned a task for valid Runnable");
            } else {
                System.out.println("Test failed: adapt returned null for valid Runnable");
            }
        } catch (Exception e) {
            System.out.println("Test failed: adapt threw exception for valid Runnable: " + e);
        }
    }
}
