/* @test    @summary Test adapt method of ForkJoinTask for Runnable input    @run main ForkJoinTaskWitupTest */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {

    public static void main(String[] args) {
        testAdaptWithRunnable();
    }

    static void testAdaptWithRunnable() {
        Runnable r = new Runnable() {
            private boolean runCalled = false;
            @Override
            public void run() {
                runCalled = true;
            }
        };
        ForkJoinTask<?> task = ForkJoinTask.adapt(r);
        if (task == null) {
            throw new AssertionError("adapt returned null for Runnable");
        }
        // Since adapt returns an instance of AdaptedRunnableAction, which is package-private,
        // we test the behavior by invoking invoke or join and checking for exceptions.
        try {
            task.invoke();
        } catch (Throwable t) {
            throw new AssertionError("invoke threw an exception", t);
        }
        // No exception means the task executed without issues.
        // Further, we can check that the task is not done before invoke, but since
        // isDone() is public, we can call it.
        boolean doneBeforeJoin = task.isDone();
        if (!doneBeforeJoin) {
            // The task should be done after invoke
            throw new AssertionError("Task not done after invoke");
        }
        // Optionally, test join() returns the same result as invoke()
        // but since adapt wraps Runnable, the result is null.
        Object result = task.join();
        if (result != null) {
            throw new AssertionError("Expected null result from join, got: " + result);
        }
    }
}
