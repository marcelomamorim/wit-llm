/*
 * @test
 * @summary Test java.util.concurrent.Executors.callable(Runnable, T) for null argument and normal behavior
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testCallableNullTask();
        testCallableNormal();
    }

    private static void testCallableNullTask() {
        try {
            Callable<Object> c = Executors.callable(null, "result");
            throw new Error("Expected NullPointerException for null task");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testCallableNormal() throws Exception {
        final boolean[] ran = new boolean[1];
        Runnable r = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };
        String result = "testResult";
        Callable<String> c = Executors.callable(r, result);
        String callResult = c.call();
        if (!ran[0]) {
            throw new Error("Runnable was not run by callable.call()");
        }
        if (!result.equals(callResult)) {
            throw new Error("Callable returned wrong result: expected " + result + " but got " + callResult);
        }
    }
}
