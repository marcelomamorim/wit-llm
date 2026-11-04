/*
 * @test
 * @summary Tests Executors.callable(PrivilegedExceptionAction) null check, result propagation, lazy execution and exception propagation
 * @run main ExecutorsWitupTest
 */

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionThrowsNPE();
        testReturnsActionResult();
        testNullResultIsReturned();
        testExecutionIsDeferredUntilCall();
        testExceptionPropagatesUnwrapped();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullActionThrowsNPE() {
        try {
            Executors.callable(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReturnsActionResult() throws Exception {
        PrivilegedExceptionAction<String> action = () -> "result";
        Callable<Object> c = Executors.callable(action);
        Object r = c.call();
        check("result".equals(r), "unexpected result: " + r);
    }

    static void testNullResultIsReturned() throws Exception {
        PrivilegedExceptionAction<Object> action = () -> null;
        Callable<Object> c = Executors.callable(action);
        check(c.call() == null, "expected null result");
    }

    static void testExecutionIsDeferredUntilCall() throws Exception {
        final int[] count = {0};
        PrivilegedExceptionAction<Object> action = () -> {
            count[0]++;
            return "x";
        };
        Callable<Object> c = Executors.callable(action);
        check(count[0] == 0, "action ran before call()");
        c.call();
        check(count[0] == 1, "action should run exactly once on call(), was " + count[0]);
        c.call();
        check(count[0] == 2, "action should run again on second call(), was " + count[0]);
    }

    static void testExceptionPropagatesUnwrapped() throws Exception {
        PrivilegedExceptionAction<Object> action = () -> {
            throw new IOException("boom");
        };
        Callable<Object> c = Executors.callable(action);
        try {
            c.call();
            throw new RuntimeException("expected IOException");
        } catch (IOException e) {
            check("boom".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }
}
