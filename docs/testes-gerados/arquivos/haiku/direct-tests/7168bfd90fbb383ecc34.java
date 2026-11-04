/*
 * @test
 * @summary Verifies InnocuousThread.setContextClassLoader allows null and rejects non-null class loaders
 * @modules java.base/jdk.internal.misc
 * @run main InnocuousThreadWitupTest
 */

public class InnocuousThreadWitupTest {

    public static void main(String[] args) throws Exception {
        Thread sys = InnocuousThread.newSystemThread(() -> { });
        if (sys.getContextClassLoader() != null) {
            throw new RuntimeException("system thread TCCL should be null");
        }
        sys.setContextClassLoader(null);
        if (sys.getContextClassLoader() != null) {
            throw new RuntimeException("TCCL should remain null after setting null");
        }

        Thread t = InnocuousThread.newThread(() -> { });
        if (t.getContextClassLoader() == null) {
            throw new RuntimeException("thread TCCL should be the system class loader");
        }
        t.setContextClassLoader(null);
        if (t.getContextClassLoader() != null) {
            throw new RuntimeException("TCCL should be cleared to null");
        }

        boolean thrown = false;
        try {
            t.setContextClassLoader(ClassLoader.getSystemClassLoader());
        } catch (SecurityException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected SecurityException for non-null class loader");
        }

        thrown = false;
        try {
            sys.setContextClassLoader(ClassLoader.getSystemClassLoader());
        } catch (SecurityException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected SecurityException for non-null class loader on system thread");
        }
    }
}
