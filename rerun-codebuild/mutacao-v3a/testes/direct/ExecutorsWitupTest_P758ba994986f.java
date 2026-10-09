/* @test    @summary Test for Executors.callable(final PrivilegedExceptionAction<?> action) method    @run main ExecutorsWitupTest_P758ba994986f */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.security.PrivilegedExceptionAction;

public class ExecutorsWitupTest_P758ba994986f {

    public static void main(String[] args) {
        testCallableWithAction()
        ;
    }

    private static void testCallableWithAction() {
        PrivilegedExceptionAction<String> action = new PrivilegedExceptionAction<String>() {
            @Override
            public String run() {
                return "Test";
            }
        };
        Callable<Object> callable = Executors.callable(action);
        try {
            Object result = callable.call();
            if (!"Test".equals(result)) {
                throw new AssertionError("Expected 'Test', got: " + result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
