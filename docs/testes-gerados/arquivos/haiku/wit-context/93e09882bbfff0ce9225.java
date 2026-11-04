/*
 * @test
 * @summary Checks ForkJoinTask.adapt(Callable) for null input and for the result of an adapted callable
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCallableThrowsNPE();
        testAdaptedTaskInvokeReturnsResult();
        testAdaptedTaskIsDoneAfterInvoke();
    }

    static void testNullCallableThrowsNPE() {
        try {
            ForkJoinTask.adapt(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("adapt(null) did not throw NullPointerException");
    }

    static void testAdaptedTaskInvokeReturnsResult() {
        Callable<String> callable = () -> "ok";
        ForkJoinTask<String> task = ForkJoinTask.adapt(callable);
        String result = task.invoke();
        if (!"ok".equals(result)) {
            throw new RuntimeException("unexpected result: " + result);
        }
    }

    static void testAdaptedTaskIsDoneAfterInvoke() {
        Callable<Integer> callable = () -> 42;
        ForkJoinTask<Integer> task = ForkJoinTask.adapt(callable);
        Integer result = task.invoke();
        if (result.intValue() != 42) {
            throw new RuntimeException("unexpected result: " + result);
        }
        if (!task.isDone()) {
            throw new RuntimeException("task not done after invoke");
        }
    }
}
