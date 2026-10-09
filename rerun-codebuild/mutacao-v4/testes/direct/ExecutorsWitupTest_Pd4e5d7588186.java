/* @test
   @summary Tests Executors.callable with a PrivilegedExceptionAction.
   @run main ExecutorsWitupTest_Pd4e5d7588186
 */
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.security.PrivilegedExceptionAction;

public class ExecutorsWitupTest_Pd4e5d7588186 {
    public static void main(String[] args) {
        testCallableWithAction();
        testCallableWithNull();
    }

    static void testCallableWithAction() {
        PrivilegedExceptionAction<String> action = new PrivilegedExceptionAction<String>() {
            public String run() {
                return "test";
            }
        };
        Callable<Object> callable = Executors.callable(action);
        try {
            Object result = callable.call();
            if (!"test".equals(result)) {
                throw new AssertionError("Expected 'test', got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }

    static void testCallableWithNull() {
        try {
            Executors.callable((PrivilegedExceptionAction<?>) null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
