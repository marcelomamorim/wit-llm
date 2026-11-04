/*
 * @test
 * @summary WITUP-derived checks for FutureTask(Runnable, V) constructor: null runnable rejection and result propagation
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.FutureTask;

public class FutureTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testNullRunnableThrowsNPE();
        testResultReturnedAfterRun();
        testNullResultReturnedAfterRun();
    }

    static void testNullRunnableThrowsNPE() {
        boolean thrown = false;
        try {
            new FutureTask<String>(null, "x");
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null runnable");
        }
    }

    static void testResultReturnedAfterRun() throws Exception {
        final boolean[] ran = new boolean[1];
        Runnable r = () -> ran[0] = true;
        FutureTask<String> task = new FutureTask<>(r, "done");
        if (task.isDone()) {
            throw new RuntimeException("Task should not be done before run()");
        }
        task.run();
        if (!ran[0]) {
            throw new RuntimeException("Runnable was not executed");
        }
        String value = task.get();
        if (!"done".equals(value)) {
            throw new RuntimeException("Unexpected result: " + value);
        }
    }

    static void testNullResultReturnedAfterRun() throws Exception {
        Runnable r = () -> { };
        FutureTask<String> task = new FutureTask<>(r, null);
        task.run();
        String value = task.get();
        if (value != null) {
            throw new RuntimeException("Expected null result but got: " + value);
        }
    }
}
