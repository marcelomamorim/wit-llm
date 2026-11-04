/*
 * @test
 * @summary Tests for InnocuousThread.setContextClassLoader method.
 * @run main InnocuousThreadWitupTest
 */

import java.security.SecurityException;
import java.lang.ClassLoader;

public class InnocuousThreadWitupTest {

    public static void main(String[] args) {
        testSetContextClassLoaderWithNull();
        testSetContextClassLoaderWithNonNull();
    }

    private static void testSetContextClassLoaderWithNull() {
        try {
            // Create an instance of InnocuousThread to test the method
            // Since the constructor is not public, use an anonymous subclass for testing
            jdk.internal.misc.InnocuousThread thread = new jdk.internal.misc.InnocuousThread() {
                @Override
                public void run() {
                    // no-op
                }
            };
            thread.setContextClassLoader(null);
            // If no exception, test passes
        } catch (SecurityException se) {
            throw new AssertionError("Unexpected SecurityException when setting null class loader", se);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }

    private static void testSetContextClassLoaderWithNonNull() {
        try {
            jdk.internal.misc.InnocuousThread thread = new jdk.internal.misc.InnocuousThread() {
                @Override
                public void run() {
                    // no-op
                }
            };
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            thread.setContextClassLoader(cl);
            throw new AssertionError("Expected SecurityException was not thrown");
        } catch (SecurityException se) {
            // Expected exception, test passes
            if (!se.getMessage().contains("setContextClassLoader")) {
                throw new AssertionError("SecurityException message mismatch", se);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
