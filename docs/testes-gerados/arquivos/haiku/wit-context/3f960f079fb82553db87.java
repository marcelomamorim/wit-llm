/*
 * @test
 * @summary Witup-generated checks for FutureTask constructor null handling and basic run/get behavior
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCallableThrowsNPE();
        testNonNullCallableRunsAndReturnsValue();
        testCallableExceptionIsWrappedInExecutionException();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullCallableThrowsNPE() {
        boolean thrown = false;
        try {
            new FutureTask<String>((Callable<String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "FutureTask(null Callable) must throw NullPointerException");
    }

    static void testNonNullCallableRunsAndReturnsValue() throws Exception {
        FutureTask<String> task = new FutureTask<String>(() -> "ok");
        check(!task.isDone(), "task must not be done before run()");
        check(!task.isCancelled(), "task must not be cancelled before run()");
        task.run();
        check(task.isDone(), "task must be done after run()");
        check("ok".equals(task.get()), "get() must return the callable result");
    }

    static void testCallableExceptionIsWrappedInExecutionException() throws Exception {
        FutureTask<String> task = new FutureTask<String>(() -> {
            throw new IllegalStateException("boom");
        });
        task.run();
        check(task.isDone(), "task must be done after run() even when callable fails");
        boolean wrapped = false;
        try {
            task.get();
        } catch (ExecutionException e) {
            wrapped = e.getCause() instanceof IllegalStateException;
        }
        check(wrapped, "get() must throw ExecutionException whose cause is the callable exception");
    }
}
