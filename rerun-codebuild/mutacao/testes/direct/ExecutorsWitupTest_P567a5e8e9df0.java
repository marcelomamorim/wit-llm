/* @test    @summary Test Executors.callable with a PrivilegedExceptionAction    @run main ExecutorsWitupTest_P567a5e8e9df0 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.security.PrivilegedExceptionAction;

public class ExecutorsWitupTest_P567a5e8e9df0 {

    public static void main(String[] args) {
        testCallableWithAction();
    }

    private static void testCallableWithAction() {
        PrivilegedExceptionAction<String> action = new PrivilegedExceptionAction<String>() {
            public String run() {
                return "test";
            }
        };
        Callable<Object> callable = Executors.callable(action);
        try {
            Object result = callable.call();
            if (!"test".equals(result)) {
                throw new AssertionError("Expected 'test', but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Callable threw an exception", e);
        }
    }
}
