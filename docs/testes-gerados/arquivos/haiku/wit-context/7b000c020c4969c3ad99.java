/*
 * @test
 * @summary Checks ForkJoinTask.adapt(Runnable, T) rejects a null runnable and adapts a non-null runnable with its result
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testNullRunnableThrowsNPE();
        testAdaptedTaskRunsRunnableAndReturnsResult();
        testAdaptedTaskAllowsNullResult();
    }

    static void testNullRunnableThrowsNPE() {
        try {
            ForkJoinTask.adapt(null, "x");
            throw new AssertionError("expected NullPointerException for null runnable");
        } catch (NullPointerException expected) {
        }
    }

    static void testAdaptedTaskRunsRunnableAndReturnsResult() {
        boolean[] ran = {false};
        Runnable r = () -> ran[0] = true;
        ForkJoinTask<String> task = ForkJoinTask.adapt(r, "res");
        if (task == null) {
            throw new AssertionError("adapt returned null");
        }
        String value = task.invoke();
        if (!"res".equals(value)) {
            throw new AssertionError("unexpected result: " + value);
        }
        if (!ran[0]) {
            throw new AssertionError("runnable was not executed");
        }
        if (!task.isDone()) {
            throw new AssertionError("task not done after invoke");
        }
    }

    static void testAdaptedTaskAllowsNullResult() {
        boolean[] ran = {false};
        Runnable r = () -> ran[0] = true;
        ForkJoinTask<String> task = ForkJoinTask.adapt(r, null);
        String value = task.invoke();
        if (value != null) {
            throw new AssertionError("expected null result but got: " + value);
        }
        if (!ran[0]) {
            throw new AssertionError("runnable was not executed");
        }
    }
}
