/* @test    @summary Test for Executors.privilegedCallable method to ensure it wraps callables correctly.    @run main ExecutorsWitupTest_P59bbe009ee26 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P59bbe009ee26 {

    public static void main(String[] args) {
        testPrivilegedCallableWrapsCallable();
        testPrivilegedCallableNullThrows();
    }

    private static void testPrivilegedCallableWrapsCallable() {
        Callable<String> original = new Callable<String>() {
            @Override
            public String call() {
                return "test";
            }
        };
        Callable<String> wrapped = Executors.privilegedCallable(original);
        if (wrapped == null) {
            throw new AssertionError("Wrapped callable should not be null");
        }
        try {
            String result = wrapped.call();
            if (!"test".equals(result)) {
                throw new AssertionError("Expected 'test', got: " + result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Callable threw an exception", e);
        }
    }

    private static void testPrivilegedCallableNullThrows() {
        try {
            Executors.privilegedCallable(null);
            throw new AssertionError("NullPointerException expected");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
