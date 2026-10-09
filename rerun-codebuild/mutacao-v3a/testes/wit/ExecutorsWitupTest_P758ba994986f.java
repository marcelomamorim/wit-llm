/* @test    @summary Test for Executors.callable(final PrivilegedExceptionAction<?>) method   @run main ExecutorsWitupTest_P758ba994986f */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.security.PrivilegedExceptionAction;

public class ExecutorsWitupTest_P758ba994986f {

    public static void main(String[] args) {
        testCallableWithNullAction();
        testCallableWithValidAction();
    }

    private static void testCallableWithNullAction() {
        try {
            Callable<?> c = Executors.callable((PrivilegedExceptionAction<?>) null);
            // Should throw NullPointerException
            System.err.println("Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null action.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
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
            if ("test".equals(result)) {
                System.out.println("Callable executed successfully with expected result.");
            } else {
                System.err.println("Unexpected result from callable: " + result);
            }
        } catch (Exception e) {
            System.err.println("Exception during callable execution: " + e);
        }
    }
}
