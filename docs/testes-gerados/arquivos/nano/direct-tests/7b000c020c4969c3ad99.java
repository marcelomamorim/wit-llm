/* @test    @summary Test adapt method of ForkJoinTask for Runnable and result    @run main ForkJoinTaskWitupTest */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {

    public static void main(String[] args) {
        testAdaptWithRunnableAndResult();
    }

    static void testAdaptWithRunnableAndResult() {
        Runnable runnable = new Runnable() {
            private boolean executed = false;
            @Override
            public void run() {
                executed = true;
            }
        };
        String result = "testResult";
        ForkJoinTask<String> task = ForkJoinTask.adapt(runnable, result);
        if (task == null) {
            throw new AssertionError("adapt returned null");
        }
        // Since adapt returns an instance of AdaptedRunnable, check its class
        boolean isAdaptedRunnable = task.getClass().getName().endsWith("AdaptedRunnable");
        if (!isAdaptedRunnable) {
            throw new AssertionError("Returned task is not an AdaptedRunnable");
        }
        // Optionally, invoke the task to verify behavior
        task.invoke();
        // Verify that the task's result is as expected
        String taskResult = task.getRawResult();
        if (!result.equals(taskResult)) {
            throw new AssertionError("Expected result: " + result + ", but got: " + taskResult);
        }
    }
}
