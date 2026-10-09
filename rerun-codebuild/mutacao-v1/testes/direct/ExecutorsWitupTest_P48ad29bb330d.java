/* @test    @summary Test for Executors.privilegedCallableUsingCurrentClassLoader method    @run main ExecutorsWitupTest_P48ad29bb330d */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest_P48ad29bb330d {

    public static void main(String[] args) {
        testPrivilegedCallableUsingCurrentClassLoaderWithNull();
        testPrivilegedCallableUsingCurrentClassLoaderWithValidCallable();
    }

    private static void testPrivilegedCallableUsingCurrentClassLoaderWithNull() {
        try {
            Executors.privilegedCallableUsingCurrentClassLoader(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    private static void testPrivilegedCallableUsingCurrentClassLoaderWithValidCallable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                return "test";
            }
        };
        try {
            Callable<String> wrapped = Executors.privilegedCallableUsingCurrentClassLoader(callable);
            if (wrapped == null) {
                throw new AssertionError("Returned callable is null");
            }
            String result = wrapped.call();
            if (!"test".equals(result)) {
                throw new AssertionError("Unexpected result from wrapped call: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during call: " + e);
        }
    }
}
