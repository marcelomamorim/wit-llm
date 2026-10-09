/*
 * @test
 * @summary Test for Executors.callable(final PrivilegedExceptionAction<?> action)
 * @run main ExecutorsWitupTest_P567a5e8e9df0
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.security.PrivilegedExceptionAction;

public class ExecutorsWitupTest_P567a5e8e9df0 {

    public static void main(String[] args) {
        testCallableWithNullAction();
        testCallableWithValidAction();
    }

    private static void testCallableWithNullAction() {
        try {
            Callable<?> c = Executors.callable((PrivilegedExceptionAction<?>) null);
            // Se não lançar exceção, falha o teste
            System.err.println("Failed: Expected NullPointerException for null action");
            System.exit(1);
        } catch (NullPointerException expected) {
            // Test passou
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
            System.exit(1);
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
                System.err.println("Failed: Unexpected result from callable: " + result);
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception during call: " + e);
            System.exit(1);
        }
    }
}
