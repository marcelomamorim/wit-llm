/*
 * @test
 * @summary Witup-generated checks for ForkJoinTask.adapt(Runnable): null argument rejection and adapted task execution
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testNullRunnableThrowsNPE();
        testAdaptedTaskRunsRunnable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullRunnableThrowsNPE() {
        try {
            ForkJoinTask.adapt(null);
            throw new AssertionError("expected NullPointerException for null runnable");
        } catch (NullPointerException expected) {
        }
    }

    static void testAdaptedTaskRunsRunnable() {
        final int[] count = {0};
        ForkJoinTask<?> task = ForkJoinTask.adapt(() -> count[0]++);
        check(task != null, "adapt returned null");
        check(!task.isDone(), "adapted task reported done before invocation");
        task.invoke();
        check(count[0] == 1, "runnable was not run exactly once, count=" + count[0]);
        check(task.isDone(), "adapted task not done after invoke");
        check(task.getRawResult() == null, "adapted task raw result should be null");
    }
}
