/* @test    @summary Test Executors.callable(Runnable, Object) for correct behavior and exception handling    @run main ExecutorsWitupTest */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testCallableWithValidRunnableAndResult();
        testCallableWithNullRunnable();
    }

    static void testCallableWithValidRunnableAndResult() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // do nothing
            }
        };
        String resultObject = "result";
        Callable<String> callable = Executors.callable(runnable, resultObject);
        try {
            String result = callable.call();
            if (!"result".equals(result)) {
                throw new AssertionError("Expected 'result', got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Callable threw an exception", e);
        }
    }

    static void testCallableWithNullRunnable() {
        try {
            Executors.callable(null, "result");
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
