/*
 * @test
 * @summary Test java.util.concurrent.Executors.callable(PrivilegedExceptionAction) for null input and normal behavior
 * @run main ExecutorsWitupTest_P4c40fef145e3
 */

import java.util.concurrent.Callable;
import java.security.PrivilegedExceptionAction;

public class ExecutorsWitupTest_P4c40fef145e3 {
    public static void main(String[] args) throws Exception {
        testNullActionThrowsNPE();
        testCallableRunsAction();
    }

    private static void testNullActionThrowsNPE() {
        try {
            Callable<Object> c = java.util.concurrent.Executors.callable(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testCallableRunsAction() throws Exception {
        final boolean[] ran = new boolean[1];
        PrivilegedExceptionAction<Object> action = new PrivilegedExceptionAction<Object>() {
            public Object run() {
                ran[0] = true;
                return "success";
            }
        };
        Callable<Object> c = java.util.concurrent.Executors.callable(action);
        Object result = c.call();
        if (!ran[0]) {
            throw new Error("Callable did not run the action");
        }
        if (!"success".equals(result)) {
            throw new Error("Callable returned unexpected result: " + result);
        }
    }
}
