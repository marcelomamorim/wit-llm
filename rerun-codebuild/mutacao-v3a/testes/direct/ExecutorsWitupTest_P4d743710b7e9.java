/* @test    @summary Test for Executors.privilegedCallableUsingCurrentClassLoader method    @run main ExecutorsWitupTest_P4d743710b7e9 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ExecutorsWitupTest_P4d743710b7e9 {

    public static void main(String[] args) {
        testPrivilegedCallableUsingCurrentClassLoader_withValidCallable();
        testPrivilegedCallableUsingCurrentClassLoader_withNullCallable();
    }

    static void testPrivilegedCallableUsingCurrentClassLoader_withValidCallable() {
        Callable<String> originalCallable = new Callable<String>() {
            @Override
            public String call() {
                return "test";
            }
        };
        Callable<String> wrappedCallable = Executors.privilegedCallableUsingCurrentClassLoader(originalCallable);
        try {
            String result = wrappedCallable.call();
            if (!"test".equals(result)) {
                throw new AssertionError("Expected 'test', got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Callable threw an exception", e);
        }
    }

    static void testPrivilegedCallableUsingCurrentClassLoader_withNullCallable() {
        try {
            Executors.privilegedCallableUsingCurrentClassLoader(null);
            throw new AssertionError("Expected NullPointerException to be thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
