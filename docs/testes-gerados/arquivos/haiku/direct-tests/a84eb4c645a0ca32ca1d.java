/*
 * @test
 * @summary Tests Executors.callable(PrivilegedAction) null check, returned value, per-call invocation and exception propagation
 * @run main ExecutorsWitupTest
 */

import java.security.PrivilegedAction;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionThrowsNPE();
        testReturnsActionResult();
        testNullResultIsReturned();
        testActionInvokedOnEachCall();
        testExceptionFromActionPropagates();
    }

    static void testNullActionThrowsNPE() {
        try {
            Executors.callable(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }

    static void testReturnsActionResult() throws Exception {
        Callable<Object> c = Executors.callable(new PrivilegedAction<String>() {
            public String run() { return "value"; }
        });
        Object result = c.call();
        if (!"value".equals(result)) {
            throw new AssertionError("Expected 'value' but got " + result);
        }
    }

    static void testNullResultIsReturned() throws Exception {
        Callable<Object> c = Executors.callable(new PrivilegedAction<Object>() {
            public Object run() { return null; }
        });
        if (c.call() != null) {
            throw new AssertionError("Expected null result");
        }
    }

    static void testActionInvokedOnEachCall() throws Exception {
        final int[] counter = {0};
        Callable<Object> c = Executors.callable(new PrivilegedAction<Integer>() {
            public Integer run() {
                counter[0]++;
                return counter[0];
            }
        });
        c.call();
        c.call();
        if (counter[0] != 2) {
            throw new AssertionError("Expected 2 invocations but got " + counter[0]);
        }
        Object third = c.call();
        if (!Integer.valueOf(3).equals(third)) {
            throw new AssertionError("Expected 3 but got " + third);
        }
    }

    static void testExceptionFromActionPropagates() throws Exception {
        Callable<Object> c = Executors.callable(new PrivilegedAction<Object>() {
            public Object run() { throw new IllegalStateException("boom"); }
        });
        try {
            c.call();
            throw new AssertionError("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            if (!"boom".equals(expected.getMessage())) {
                throw new AssertionError("Unexpected message: " + expected.getMessage());
            }
        }
    }
}
