/* @test    @summary Test for Executors.callable(final PrivilegedExceptionAction<?> action) method.
 * @run main ExecutorsWitupTest_Pc4f3552f809e
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.security.PrivilegedExceptionAction;

public class ExecutorsWitupTest_Pc4f3552f809e {

    public static void main(String[] args) {
        testCallableWithNullAction();
        testCallableWithValidAction();
    }

    private static void testCallableWithNullAction() {
        try {
            Executors.callable((PrivilegedExceptionAction<?>) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }

    private static void testCallableWithValidAction() {
        PrivilegedExceptionAction<String> action = new PrivilegedExceptionAction<String>() {
            public String run() {
                return "test";
            }
        };
        try {
            Callable<?> c = Executors.callable(action);
            Object result = c.call();
            if (!"test".equals(result)) {
                throw new AssertionError("Unexpected result: " + result);
            }
        } catch (Throwable t) {
            throw new AssertionError("Exception during callable execution: " + t, t);
        }
    }
}
