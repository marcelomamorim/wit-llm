/*
 * @test
 * @summary Verifies ForkJoinTask.adapt(Runnable, T) runs the runnable on invoke and exposes the supplied result
 * @run main ForkJoinTaskWitupTest
 */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {
    static int runs = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Runnable r = () -> runs++;
        ForkJoinTask<String> t = ForkJoinTask.adapt(r, "result");
        check(t != null, "adapt returned null");
        check(!t.isDone(), "task should not be done before invoke");
        check(runs == 0, "runnable should not run before invoke");

        String v = t.invoke();
        check(runs == 1, "runnable should run exactly once on invoke, runs=" + runs);
        check("result".equals(v), "invoke should return the adapted result");
        check(t.isDone(), "task should be done after invoke");
        check("result".equals(t.getRawResult()), "getRawResult should return the adapted result");
        check("result".equals(t.join()), "join should return the adapted result");
        check(runs == 1, "join must not re-run the runnable, runs=" + runs);

        ForkJoinTask<Object> n = ForkJoinTask.adapt(() -> runs++, null);
        Object nv = n.invoke();
        check(nv == null, "null result should be returned as null");
        check(n.isDone(), "task with null result should be done after invoke");
        check(runs == 2, "runnable should run for null-result task, runs=" + runs);
    }
}
