/*
 * @test
 * @summary Verifies Executors.callable(Runnable, T): null task throws NullPointerException; returned Callable runs the task on each call and returns the given result.
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        final int[] runs = {0};
        Runnable task = () -> runs[0]++;

        Callable<String> c = Executors.callable(task, "result");
        String r = c.call();
        if (!"result".equals(r)) {
            throw new RuntimeException("unexpected result: " + r);
        }
        if (runs[0] != 1) {
            throw new RuntimeException("task runs after first call: " + runs[0]);
        }

        c.call();
        if (runs[0] != 2) {
            throw new RuntimeException("task runs after second call: " + runs[0]);
        }

        Callable<Object> nullResult = Executors.callable(() -> { }, null);
        if (nullResult.call() != null) {
            throw new RuntimeException("expected null result");
        }

        boolean npe = false;
        try {
            Executors.callable(null, "x");
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null task");
        }
    }
}
