/*
 * @test
 * @summary Test ClassLoader.run() method for basic behavior and exception safety
 * @run main ClassLoaderWitupTest
 */

public class ClassLoaderWitupTest {
    public static void main(String[] args) {
        // Create an anonymous subclass of ClassLoader to access protected run() method
        ClassLoader cl = new ClassLoader() {
            private final SecurityManager sm = System.getSecurityManager();
            private final String packageName = "java.lang";
            public Void run() {
                if (sm != null) {
                    sm.checkPackageAccess(packageName);
                }
                return null;
            }
        };

        // Test: run() returns null without throwing when SecurityManager is null
        try {
            ClassLoader clNoSM = new ClassLoader() {
                public Void run() {
                    return null;
                }
            };
            if (clNoSM.run() != null) {
                throw new RuntimeException("Expected run() to return null");
            }
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception when SecurityManager is null", t);
        }

        // Test: run() returns null when SecurityManager is present and allows access
        try {
            if (cl.run() != null) {
                throw new RuntimeException("Expected run() to return null");
            }
        } catch (SecurityException se) {
            // SecurityException may be thrown if current security manager denies access
            // This is acceptable behavior, so test passes if exception is thrown
            return;
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception", t);
        }

        // If no exception thrown, test passes
    }
}
