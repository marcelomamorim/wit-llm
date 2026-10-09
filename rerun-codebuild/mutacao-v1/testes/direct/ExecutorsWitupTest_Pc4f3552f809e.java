/* @test    @summary Test Executors.callable with a simple PrivilegedExceptionAction    @run main ExecutorsWitupTest_Pc4f3552f809e */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.security.PrivilegedExceptionAction;

public class ExecutorsWitupTest_Pc4f3552f809e {

    public static void main(String[] args) {
        testCallableWithAction();
    }

    static void testCallableWithAction() {
        PrivilegedExceptionAction<String> action = new PrivilegedExceptionAction<String>() {
            public String run() {
                return "test";
            }
        };
        Callable<Object> task = Executors.callable(action);
        try {
            Object result = task.call();
            if (!"test".equals(result)) {
                throw new AssertionError("Expected 'test', but got: " + result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Callable execution failed", e);
        }
    }
}
