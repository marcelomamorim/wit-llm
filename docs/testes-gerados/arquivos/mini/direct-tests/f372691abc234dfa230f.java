/*
 * @test
 * @summary Test FutureTask(Runnable, V) constructor behavior
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.FutureTask;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;

public class FutureTaskWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithRunnableAndResult();
    }

    static void testConstructorWithRunnableAndResult() throws Exception {
        final boolean[] ran = new boolean[1];
        Runnable r = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };
        String result = "testResult";
        FutureTask<String> ft = new FutureTask<>(r, result);

        // Before run, get should block, so run it in a thread
        Thread t = new Thread(ft);
        t.start();
        t.join();

        // After run, get should return the result passed to constructor
        String got = ft.get();

        if (!ran[0]) {
            throw new AssertionError("Runnable was not run");
        }
        if (!result.equals(got)) {
            throw new AssertionError("FutureTask.get() returned unexpected result: " + got);
        }
    }
}
