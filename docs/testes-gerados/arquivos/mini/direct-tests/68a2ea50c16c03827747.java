/*
 * @test
 * @summary Test Runtime.addShutdownHook behavior
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.getRuntime();

        // Test adding a valid shutdown hook thread
        Thread hook = new Thread() {
            public void run() {
                // no-op
            }
        };
        runtime.addShutdownHook(hook);

        // Test adding a null hook - expect NullPointerException
        boolean npeThrown = false;
        try {
            runtime.addShutdownHook(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when adding null shutdown hook");
        }

        // Test SecurityManager checkPermission call
        // Install a SecurityManager that denies RuntimePermission("shutdownHooks")
        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            public void checkPermission(java.security.Permission perm) {
                if (perm instanceof RuntimePermission && "shutdownHooks".equals(perm.getName())) {
                    throw new SecurityException("shutdownHooks permission denied");
                }
            }
            public void checkPermission(java.security.Permission perm, Object context) {
                checkPermission(perm);
            }
        });

        boolean seThrown = false;
        try {
            runtime.addShutdownHook(new Thread());
        } catch (SecurityException e) {
            seThrown = true;
        } finally {
            System.setSecurityManager(original);
        }

        if (!seThrown) {
            throw new RuntimeException("Expected SecurityException when permission denied");
        }
    }
}
