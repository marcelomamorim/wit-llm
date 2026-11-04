/*
 * @test
 * @summary Verify ForkJoinTask.adapt(Callable) result propagation and exception behavior
 * @run main ForkJoinTaskWitupTest
 */

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testResultViaJoin();
        testResultViaInvoke();
        testDistinctTasks();
        testRuntimeExceptionPropagates();
        testCheckedExceptionWrapped();
    }

    static void testResultViaJoin() {
        ForkJoinTask<String> t = ForkJoinTask.adapt(() -> "ok");
        check("ok".equals(t.join()), "join should return callable result");
        check(t.isDone(), "task should be done after join");
    }

    static void testResultViaInvoke() {
        ForkJoinTask<Integer> t = ForkJoinTask.adapt(() -> 42);
        Integer r = t.invoke();
        check(r.intValue() == 42, "invoke should return callable result");
    }

    static void testDistinctTasks() {
        Callable<String> c = () -> "x";
        ForkJoinTask<String> a = ForkJoinTask.adapt(c);
        ForkJoinTask<String> b = ForkJoinTask.adapt(c);
        check(a != b, "adapt should return a new task each call");
        check("x".equals(a.join()), "first adapted task result");
        check("x".equals(b.join()), "second adapted task result");
    }

    static void testRuntimeExceptionPropagates() {
        ForkJoinTask<String> t = ForkJoinTask.adapt(() -> {
            throw new IllegalStateException("boom-rt");
        });
        try {
            t.join();
            throw new RuntimeException("expected exception was not thrown");
        } catch (IllegalStateException e) {
            check("boom-rt".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
        check(t.isCompletedAbnormally(), "task should complete abnormally");
    }

    static void testCheckedExceptionWrapped() {
        ForkJoinTask<String> t = ForkJoinTask.adapt(() -> {
            throw new IOException("boom-io");
        });
        try {
            t.join();
            throw new RuntimeException("expected exception was not thrown");
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause == null && e instanceof IOException) {
                return;
            }
            check(cause instanceof IOException, "checked exception should be wrapped as cause, got: " + e);
            check("boom-io".equals(cause.getMessage()), "unexpected cause message");
        }
        check(t.isCompletedAbnormally(), "task should complete abnormally");
    }
}
