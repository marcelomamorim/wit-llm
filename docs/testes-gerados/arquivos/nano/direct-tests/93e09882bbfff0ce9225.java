/* @test    @summary Test ForkJoinTask.adapt method for correct behavior    @run main ForkJoinTaskWitupTest */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {

    public static void main(String[] args) {
        testAdaptWithNullCallable();
        testAdaptWithSimpleCallable();
    }

    static void testAdaptWithNullCallable() {
        try {
            ForkJoinTask<?> task = ForkJoinTask.adapt(null);
            if (task == null) {
                System.out.println("testAdaptWithNullCallable: Passed (null returns null)");
            } else {
                System.out.println("testAdaptWithNullCallable: Failed (expected null)");
            }
        } catch (NullPointerException e) {
            System.out.println("testAdaptWithNullCallable: Passed (throws NullPointerException)");
        } catch (Exception e) {
            System.out.println("testAdaptWithNullCallable: Failed (unexpected exception: " + e + ")");
        }
    }

    static void testAdaptWithSimpleCallable() {
        java.util.concurrent.Callable<Integer> callable = new java.util.concurrent.Callable<Integer>() {
            public Integer call() {
                return 42;
            }
        };
        ForkJoinTask<Integer> task = ForkJoinTask.adapt(callable);
        if (task != null) {
            System.out.println("testAdaptWithSimpleCallable: Passed (task created)");
        } else {
            System.out.println("testAdaptWithSimpleCallable: Failed (task is null)");
        }
        // Optionally, test invoke to see if it executes correctly
        try {
            Integer result = task.invoke();
            if (result != null && result == 42) {
                System.out.println("testAdaptWithSimpleCallable: invoke() returned expected result");
            } else {
                System.out.println("testAdaptWithSimpleCallable: invoke() returned unexpected result: " + result);
            }
        } catch (Exception e) {
            System.out.println("testAdaptWithSimpleCallable: invoke() threw exception: " + e);
        }
    }
}
