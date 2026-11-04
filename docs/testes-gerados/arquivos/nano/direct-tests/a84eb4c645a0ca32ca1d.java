/* @test    @summary Test Executors.callable with a PrivilegedAction that returns a string    @run main ExecutorsWitupTest */

import java.util.concurrent.Callable;
import java.security.PrivilegedAction;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testCallableWithPrivilegedAction();
    }

    public static void testCallableWithPrivilegedAction() {
        PrivilegedAction<String> action = new PrivilegedAction<String>() {
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
            throw new RuntimeException(e);
        }
    }
}
