/*
 * @test
 * @summary Test java.util.concurrent.Executors.callable(Runnable, T) method
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testCallableReturnsResult();
        testCallableThrowsNPEOnNullTask();
    }

    static void testCallableReturnsResult() throws Exception {
        final boolean[] ran = new boolean[1];
        Runnable r = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };
        String expectedResult = "result";
        Callable<String> c = java.util.concurrent.Executors.callable(r, expectedResult);
        String actualResult = c.call();
        if (!ran[0]) {
            throw new AssertionError("Runnable was not run by callable.call()");
        }
        if (!expectedResult.equals(actualResult)) {
            throw new AssertionError("Callable returned wrong result: expected " + expectedResult + " but got " + actualResult);
        }
    }

    static void testCallableThrowsNPEOnNullTask() {
        try {
            java.util.concurrent.Executors.callable(null, "irrelevant");
            throw new AssertionError("Expected NullPointerException for null task");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
