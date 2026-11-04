/* @test    @summary Test Executors.callable(Runnable) method for correctness and null handling    @run main ExecutorsWitupTest */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testCallableWithValidRunnable();
        testCallableWithNullRunnable();
    }

    static void testCallableWithValidRunnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // do nothing
            }
        };
        Callable<Object> callable = Executors.callable(runnable);
        Object result = null;
        try {
            result = callable.call();
        } catch (Exception e) {
            throw new RuntimeException("Callable threw an exception", e);
        }
        if (result != null) {
            throw new AssertionError("Expected null result from callable.call(), but got: " + result);
        }
    }

    static void testCallableWithNullRunnable() {
        try {
            Executors.callable(null);
            throw new AssertionError("Expected NullPointerException for null Runnable");
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
