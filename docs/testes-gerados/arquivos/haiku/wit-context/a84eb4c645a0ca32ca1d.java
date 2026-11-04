/*
 * @test
 * @summary Tests Executors.callable(PrivilegedAction) null handling and delegation to PrivilegedAction.run()
 * @run main ExecutorsWitupTest
 */

import java.security.PrivilegedAction;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullActionThrowsNPE();
        testCallableDelegatesToRun();
        testCallableReturnsNullWhenRunReturnsNull();
        testRunIsDeferredUntilCall();
    }

    static void testNullActionThrowsNPE() {
        PrivilegedAction<?> action = null;
        try {
            Executors.callable(action);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }

    static void testCallableDelegatesToRun() throws Exception {
        PrivilegedAction<String> action = () -> "value";
        Callable<Object> callable = Executors.callable(action);
        Object result = callable.call();
        if (!"value".equals(result)) {
            throw new RuntimeException("Unexpected result: " + result);
        }
    }

    static void testCallableReturnsNullWhenRunReturnsNull() throws Exception {
        PrivilegedAction<String> action = () -> null;
        Callable<Object> callable = Executors.callable(action);
        Object result = callable.call();
        if (result != null) {
            throw new RuntimeException("Expected null result but got: " + result);
        }
    }

    static void testRunIsDeferredUntilCall() throws Exception {
        int[] runs = new int[1];
        PrivilegedAction<Integer> action = () -> {
            runs[0]++;
            return runs[0];
        };
        Callable<Object> callable = Executors.callable(action);
        if (runs[0] != 0) {
            throw new RuntimeException("run() invoked before call(): " + runs[0]);
        }
        Object result = callable.call();
        if (runs[0] != 1 || !Integer.valueOf(1).equals(result)) {
            throw new RuntimeException("Unexpected state after call: runs=" + runs[0] + ", result=" + result);
        }
    }
}
