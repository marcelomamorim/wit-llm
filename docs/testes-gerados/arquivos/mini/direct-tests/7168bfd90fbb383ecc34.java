/*
 * @test
 * @summary Test jdk.internal.misc.InnocuousThread.setContextClassLoader behavior
 * @run main InnocuousThreadWitupTest
 */

import jdk.internal.misc.InnocuousThread;

public class InnocuousThreadWitupTest {
    public static void main(String[] args) {
        InnocuousThread t = InnocuousThread.newThread(() -> {});

        // Test that setting context class loader to null succeeds
        t.setContextClassLoader(null);
        if (t.getContextClassLoader() != null) {
            throw new RuntimeException("Expected context class loader to be null after setContextClassLoader(null)");
        }

        // Test that setting context class loader to non-null throws SecurityException
        boolean threw = false;
        try {
            t.setContextClassLoader(ClassLoader.getSystemClassLoader());
        } catch (SecurityException se) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected SecurityException when setting non-null context class loader");
        }
    }
}
