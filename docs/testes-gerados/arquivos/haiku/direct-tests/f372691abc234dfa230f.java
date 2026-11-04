/*
 * @test
 * @summary FutureTask(Runnable, V) runs the runnable once and exposes the given result via get()
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testResultReturnedAfterRun();
        testNullResultReturned();
        testSecondRunIsNoOp();
        testExceptionFromRunnablePropagatesAsExecutionException();
        testCancelBeforeRunPreventsExecution();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testResultReturnedAfterRun() throws Exception {
        int[] runs = {0};
        Runnable r = () -> runs[0]++;
        FutureTask<String> ft = new FutureTask<>(r, "value");
        check(!ft.isDone(), "task must not be done before run");
        check(runs[0] == 0, "runnable must not run before run()");
        ft.run();
        check(runs[0] == 1, "runnable must run exactly once");
        check(ft.isDone(), "task must be done after run");
        check("value".equals(ft.get()), "get() must return the supplied result");
    }

    static void testNullResultReturned() throws Exception {
        int[] runs = {0};
        Runnable r = () -> runs[0]++;
        FutureTask<String> ft = new FutureTask<>(r, null);
        ft.run();
        check(runs[0] == 1, "runnable must run once");
        check(ft.get() == null, "get() must return null when null result supplied");
    }

    static void testSecondRunIsNoOp() throws Exception {
        int[] runs = {0};
        Runnable r = () -> runs[0]++;
        FutureTask<Integer> ft = new FutureTask<>(r, Integer.valueOf(7));
        ft.run();
        ft.run();
        check(runs[0] == 1, "second run() must not execute the runnable again");
        check(ft.get().intValue() == 7, "result must remain the supplied value");
    }

    static void testExceptionFromRunnablePropagatesAsExecutionException() throws Exception {
        IllegalStateException boom = new IllegalStateException("boom");
        Runnable r = () -> { throw boom; };
        FutureTask<String> ft = new FutureTask<>(r, "unused");
        ft.run();
        check(ft.isDone(), "task must be done after failing run");
        try {
            ft.get();
            throw new RuntimeException("get() should have thrown ExecutionException");
        } catch (ExecutionException e) {
            check(e.getCause() == boom, "cause must be the runnable's exception");
        }
    }

    static void testCancelBeforeRunPreventsExecution() throws Exception {
        int[] runs = {0};
        Runnable r = () -> runs[0]++;
        FutureTask<String> ft = new FutureTask<>(r, "value");
        check(ft.cancel(false), "cancel of a new task must succeed");
        ft.run();
        check(runs[0] == 0, "runnable must not execute after cancel");
        check(ft.isCancelled(), "task must report cancelled");
        check(ft.isDone(), "cancelled task must report done");
    }
}
