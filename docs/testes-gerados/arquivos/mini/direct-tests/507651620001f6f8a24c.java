/*
 * @test
 * @summary Test java.util.concurrent.Executors.callable(PrivilegedExceptionAction) method
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.security.PrivilegedExceptionAction;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testCallableWithValidAction();
        testCallableWithNullAction();
    }

    static void testCallableWithValidAction() throws Exception {
        final String expected = "success";
        PrivilegedExceptionAction<String> action = new PrivilegedExceptionAction<String>() {
            public String run() {
                return expected;
            }
        };

        Callable<Object> callable = Executors.callable(action);
        Object result = callable.call();
        if (!expected.equals(result)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    static void testCallableWithNullAction() {
        try {
            Executors.callable(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
