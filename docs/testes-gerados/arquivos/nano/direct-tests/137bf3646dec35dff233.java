/* @test    @summary Test for Executors.privilegedCallable method    @run main ExecutorsWitupTest */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testPrivilegedCallableWithValidCallable();
        testPrivilegedCallableWithNull();
    }

    private static void testPrivilegedCallableWithValidCallable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                return "test";
            }
        };
        Callable<String> privileged = Executors.privilegedCallable(callable);
        String result = null;
        try {
            result = privileged.call();
        } catch (Exception e) {
            throw new RuntimeException("Callable execution failed", e);
        }
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', but got: " + result);
        }
    }

    private static void testPrivilegedCallableWithNull() {
        try {
            Executors.privilegedCallable(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
