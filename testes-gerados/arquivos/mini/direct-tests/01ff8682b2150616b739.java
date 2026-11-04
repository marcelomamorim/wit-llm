/*
 * @test
 * @summary Test java.util.concurrent.Executors.callable(Runnable) method
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testCallableWithValidRunnable();
        testCallableWithNullRunnable();
    }

    static void testCallableWithValidRunnable() throws Exception {
        final boolean[] ran = new boolean[1];
        Runnable r = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };
        Callable<Object> c = Executors.callable(r);
        if (c == null) {
            throw new RuntimeException("Callable returned is null");
        }
        Object result = c.call();
        if (result != null) {
            throw new RuntimeException("Callable.call() should return null");
        }
        if (!ran[0]) {
            throw new RuntimeException("Runnable was not run by Callable.call()");
        }
    }

    static void testCallableWithNullRunnable() {
        try {
            Executors.callable(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
