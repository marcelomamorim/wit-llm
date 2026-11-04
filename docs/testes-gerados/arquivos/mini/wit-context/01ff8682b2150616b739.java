/*
 * @test
 * @summary Test java.util.concurrent.Executors.callable(Runnable) for null input and normal behavior
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testCallableNullThrows();
        testCallableReturnsRunnableAdapter();
    }

    static void testCallableNullThrows() {
        try {
            Executors.callable(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testCallableReturnsRunnableAdapter() throws Exception {
        final boolean[] ran = new boolean[1];
        Runnable r = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };
        Callable<Object> c = Executors.callable(r);
        if (c == null) {
            throw new Error("Callable returned is null");
        }
        Object result = c.call();
        if (result != null) {
            throw new Error("Callable.call() should return null but returned " + result);
        }
        if (!ran[0]) {
            throw new Error("Runnable.run() was not executed by Callable.call()");
        }
    }
}
