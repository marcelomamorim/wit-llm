/* @test    @summary Test for Executors.privilegedCallableUsingCurrentClassLoader method    @run main ExecutorsWitupTest */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testPrivilegedCallableUsingCurrentClassLoaderWithNull();
        testPrivilegedCallableUsingCurrentClassLoaderWithValidCallable();
    }

    private static void testPrivilegedCallableUsingCurrentClassLoaderWithNull() {
        try {
            Executors.privilegedCallableUsingCurrentClassLoader(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testPrivilegedCallableUsingCurrentClassLoaderWithValidCallable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                return "test";
            }
        };
        Callable<String> wrappedCallable = Executors.privilegedCallableUsingCurrentClassLoader(callable);
        try {
            String result = wrappedCallable.call();
            if (!"test".equals(result)) {
                System.err.println("Unexpected result from wrapped callable: " + result);
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Exception thrown during call(): " + e);
            System.exit(1);
        }
    }
}
