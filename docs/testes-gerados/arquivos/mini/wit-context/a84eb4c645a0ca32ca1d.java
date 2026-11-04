/*
 * @test
 * @summary Test java.util.concurrent.Executors.callable(PrivilegedAction) for NPE and normal behavior
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.security.PrivilegedAction;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testCallableNullAction();
        testCallableNormalAction();
    }

    private static void testCallableNullAction() {
        try {
            Executors.callable(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testCallableNormalAction() throws Exception {
        final String expected = "success";
        PrivilegedAction<String> action = new PrivilegedAction<String>() {
            public String run() {
                return expected;
            }
        };
        Callable<Object> callable = Executors.callable(action);
        Object result = callable.call();
        if (!expected.equals(result)) {
            throw new Error("Callable returned unexpected result: " + result);
        }
    }
}
