/*
 * @test
 * @summary Test Runtime.addShutdownHook(Thread) for security and state exceptions
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;
import java.lang.Thread;
import java.lang.SecurityManager;
import java.lang.RuntimePermission;

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        // Test normal usage: add a valid shutdown hook thread
        Thread hook = new Thread() {
            public void run() {
                // no-op
            }
        };
        runtime.addShutdownHook(hook);

        // Test security manager check: install a SecurityManager that denies shutdownHooks permission
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
                if ("shutdownHooks".equals(perm.getName())) {
                    throw new SecurityException("Permission denied: " + perm.getName());
                }
            }

            @Override
            public void checkPermission(java.security.Permission perm, Object context) {
                checkPermission(perm);
            }
        };

        System.setSecurityManager(sm);

        boolean securityExceptionThrown = false;
        try {
            runtime.addShutdownHook(new Thread());
        } catch (SecurityException se) {
            securityExceptionThrown = true;
        } finally {
            System.setSecurityManager(null); // restore
        }

        if (!securityExceptionThrown) {
            throw new RuntimeException("Expected SecurityException was not thrown when adding shutdown hook with restrictive SecurityManager");
        }

        // Test null argument: adding null as shutdown hook should throw NullPointerException
        boolean npeThrown = false;
        try {
            runtime.addShutdownHook(null);
        } catch (NullPointerException npe) {
            npeThrown = true;
        }

        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException was not thrown when adding null shutdown hook");
        }
    }
}
