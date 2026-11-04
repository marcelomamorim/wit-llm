/*
 * @test
 * @summary FutureTask(Callable) constructor and basic run/get behavior
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCallableThrowsNPE();
        testNormalCompletion();
        testExceptionPropagation();
        testRunOnlyOnce();
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testNullCallableThrowsNPE() {
        boolean thrown = false;
        try {
            new FutureTask<String>((Callable<String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("expected NullPointerException for null callable");
        }
    }

    static void testNormalCompletion() throws Exception {
        FutureTask<String> task = new FutureTask<String>(() -> "result");
        if (task.isDone()) {
            fail("task should not be done before run");
        }
        if (task.isCancelled()) {
            fail("task should not be cancelled before run");
        }
        task.run();
        if (!task.isDone()) {
            fail("task should be done after run");
        }
        if (!"result".equals(task.get())) {
            fail("unexpected result from get");
        }
    }

    static void testExceptionPropagation() throws Exception {
        FutureTask<String> task = new FutureTask<String>(() -> {
            throw new IllegalStateException("boom");
        });
        task.run();
        if (!task.isDone()) {
            fail("task should be done after failing run");
        }
        boolean wrapped = false;
        try {
            task.get();
        } catch (ExecutionException e) {
            wrapped = (e.getCause() instanceof IllegalStateException);
        }
        if (!wrapped) {
            fail("expected ExecutionException wrapping IllegalStateException");
        }
    }

    static void testRunOnlyOnce() throws Exception {
        int[] count = {0};
        FutureTask<Integer> task = new FutureTask<Integer>(() -> ++count[0]);
        task.run();
        task.run();
        if (count[0] != 1) {
            fail("callable should run only once, ran " + count[0] + " times");
        }
        if (task.get().intValue() != 1) {
            fail("unexpected result after repeated run");
        }
    }
}
