/*
 * @test
 * @summary Test for ForkJoinTask.adapt method behavior
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Callable;

public class ForkJoinTaskWitupTest {

    public static void main(String[] args) {
        testAdaptWithNullCallable();
        testAdaptWithValidCallable();
    }

    private static void testAdaptWithNullCallable() {
        try {
            ForkJoinTask<?> task = ForkJoinTask.adapt(null);
            // Expecting a NullPointerException or no exception depending on implementation
            // Since the source code does not explicitly throw, test for null return or behavior
            if (task == null) {
                System.out.println("PASS: adapt(null) returned null as expected.");
            } else {
                System.out.println("FAIL: adapt(null) did not return null.");
            }
        } catch (NullPointerException e) {
            System.out.println("PASS: adapt(null) threw NullPointerException as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: adapt(null) threw unexpected exception: " + e);
        }
    }

    private static void testAdaptWithValidCallable() {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                return 42;
            }
        };
        try {
            ForkJoinTask<Integer> task = ForkJoinTask.adapt(callable);
            if (task != null) {
                System.out.println("PASS: adapt(callable) returned a task.");
            } else {
                System.out.println("FAIL: adapt(callable) returned null.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: adapt(callable) threw exception: " + e);
        }
    }
}
