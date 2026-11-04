/*
 * @test
 * @summary Test jdk.internal.misc.InnocuousThread.setContextClassLoader behavior
 * @run main InnocuousThreadWitupTest
 */

import jdk.internal.misc.InnocuousThread;

public class InnocuousThreadWitupTest {
    public static void main(String[] args) {
        InnocuousThread t = (InnocuousThread) InnocuousThread.newThread(() -> {});

        // Test that setting context class loader to null is allowed
        t.setContextClassLoader(null);

        // Test that setting context class loader to non-null throws SecurityException
        boolean threw = false;
        try {
            t.setContextClassLoader(ClassLoader.getSystemClassLoader());
        } catch (SecurityException se) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected SecurityException when setting non-null context class loader");
        }
    }
}
