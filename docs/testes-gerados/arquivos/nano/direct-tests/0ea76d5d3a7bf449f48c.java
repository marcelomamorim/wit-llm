/*
 * @test
 * @summary Test ForkJoinPool.invoke(ForkJoinTask) method for basic behavior
 * @run main ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) {
        testInvokeWithNullTask();
        testInvokeWithSimpleTask();
    }

    static void testInvokeWithNullTask() {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.invoke(null);
            System.out.println("Failed: Expected NullPointerException for null task");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception: " + t);
        }
    }

    static void testInvokeWithSimpleTask() {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> task = new ForkJoinTask<Integer>() {
            private boolean isDone = false;
            @Override
            public Integer getRawResult() {
                return 42;
            }
            @Override
            protected void setRawResult(Integer value) {
                // no-op
            }
            @Override
            protected boolean exec() {
                isDone = true;
                return true;
            }
            @Override
            public boolean isDone() {
                return isDone;
            }
        };
        try {
            Integer result = pool.invoke(task);
            if (result != null && result == 42) {
                System.out.println("Passed: invoke returned expected result 42");
            } else {
                System.out.println("Failed: invoke returned unexpected result: " + result);
            }
        } catch (Throwable t) {
            System.out.println("Failed: Unexpected exception during invoke: " + t);
        }
    }
}
