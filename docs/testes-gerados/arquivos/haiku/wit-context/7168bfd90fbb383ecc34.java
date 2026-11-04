/*
 * @test
 * @summary Verify InnocuousThread.setContextClassLoader accepts null and rejects non-null class loaders with SecurityException
 * @modules java.base/jdk.internal.misc
 * @run main InnocuousThreadWitupTest
 */

import jdk.internal.misc.InnocuousThread;

public class InnocuousThreadWitupTest {
    public static void main(String[] args) throws Exception {
        Thread cleared = InnocuousThread.newSystemThread(() -> { });
        cleared.setContextClassLoader(null);
        if (cleared.getContextClassLoader() != null) {
            throw new RuntimeException("Context class loader should be null after clearing with null");
        }

        Thread rejected = InnocuousThread.newSystemThread(() -> { });
        boolean thrown = false;
        try {
            rejected.setContextClassLoader(ClassLoader.getSystemClassLoader());
        } catch (SecurityException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected SecurityException for non-null class loader");
        }
        if (rejected.getContextClassLoader() != null) {
            throw new RuntimeException("Context class loader must remain null after rejected update");
        }
    }
}
