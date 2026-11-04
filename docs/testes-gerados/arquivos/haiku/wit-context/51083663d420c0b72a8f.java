/*
 * @test
 * @summary Witup-generated test for Executors.callable(Runnable, T) null-task guard and adapter behavior
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        try {
            Executors.callable(null, "x");
            throw new AssertionError("expected NullPointerException for null task");
        } catch (NullPointerException expected) {
        }

        final int[] runs = {0};
        Runnable r = () -> runs[0]++;
        Object marker = new Object();
        Callable<Object> c = Executors.callable(r, marker);
        if (c == null) {
            throw new AssertionError("callable returned null");
        }
        Object res = c.call();
        if (res != marker) {
            throw new AssertionError("callable did not return the supplied result");
        }
        if (runs[0] != 1) {
            throw new AssertionError("task was not run exactly once, runs=" + runs[0]);
        }

        Callable<String> c2 = Executors.callable(r, null);
        if (c2.call() != null) {
            throw new AssertionError("null result was not preserved");
        }
        if (runs[0] != 2) {
            throw new AssertionError("second task was not run, runs=" + runs[0]);
        }
    }
}
