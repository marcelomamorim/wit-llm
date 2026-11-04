/*
 * @test
 * @summary Tests Executors.callable(PrivilegedExceptionAction) null rejection and call() delegation
 * @run main ExecutorsWitupTest
 */

import java.security.PrivilegedExceptionAction;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionThrowsNullPointerException();
        testCallReturnsActionResult();
        testCallPropagatesActionRuntimeException();
    }

    static void testNullActionThrowsNullPointerException() {
        try {
            Executors.callable(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null action");
    }

    static void testCallReturnsActionResult() throws Exception {
        PrivilegedExceptionAction<String> action = () -> "result";
        Callable<Object> callable = Executors.callable(action);
        Object value = callable.call();
        if (!"result".equals(value)) {
            throw new AssertionError("unexpected result: " + value);
        }
    }

    static void testCallPropagatesActionRuntimeException() throws Exception {
        PrivilegedExceptionAction<Object> action = () -> {
            throw new IllegalStateException("boom");
        };
        Callable<Object> callable = Executors.callable(action);
        try {
            callable.call();
        } catch (IllegalStateException expected) {
            if (!"boom".equals(expected.getMessage())) {
                throw new AssertionError("unexpected message: " + expected.getMessage());
            }
            return;
        }
        throw new AssertionError("expected IllegalStateException from action");
    }
}
