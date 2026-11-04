/*
 * @test
 * @summary Test java.util.concurrent.Executors.callable(PrivilegedAction) method
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.security.PrivilegedAction;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testCallableWithValidAction();
        testCallableWithNullAction();
    }

    static void testCallableWithValidAction() throws Exception {
        final String expected = "test";
        PrivilegedAction<String> action = new PrivilegedAction<String>() {
            public String run() {
                return expected;
            }
        };
        Callable<Object> callable = java.util.concurrent.Executors.callable(action);
        Object result = callable.call();
        if (!expected.equals(result)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    static void testCallableWithNullAction() {
        try {
            java.util.concurrent.Executors.callable(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
