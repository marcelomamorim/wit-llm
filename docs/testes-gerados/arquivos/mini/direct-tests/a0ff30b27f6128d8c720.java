/*
 * @test
 * @summary Test Runtime.removeShutdownHook behavior including security check and removal
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;
import java.lang.Thread;
import java.lang.SecurityManager;
import java.lang.RuntimePermission;

public class RuntimeWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveShutdownHookWithoutSecurityManager();
        testRemoveShutdownHookWithAllowingSecurityManager();
        testRemoveShutdownHookWithDenyingSecurityManager();
    }

    private static void testRemoveShutdownHookWithoutSecurityManager() {
        // No SecurityManager installed, removal should proceed without exception
        Runtime rt = Runtime.getRuntime();
        Thread hook = new Thread();
        // Removal returns false since hook was never added
        boolean removed = rt.removeShutdownHook(hook);
        if (removed) {
            throw new RuntimeException("Expected removeShutdownHook to return false for non-registered hook");
        }
    }

    private static void testRemoveShutdownHookWithAllowingSecurityManager() {
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
                if (perm instanceof RuntimePermission && "shutdownHooks".equals(perm.getName())) {
                    // allow
                    return;
                }\n                // allow other permissions
            }

            @Override
            public void checkExit(int status) {
                // allow exit
            }
        };

        SecurityManager old = System.getSecurityManager();
        try {
            System.setSecurityManager(sm);
            Runtime rt = Runtime.getRuntime();
            Thread hook = new Thread();
            // Removal returns false since hook was never added
            boolean removed = rt.removeShutdownHook(hook);
            if (removed) {
                throw new RuntimeException("Expected removeShutdownHook to return false for non-registered hook");
            }
        } finally {
            System.setSecurityManager(old);
        }
    }

    private static void testRemoveShutdownHookWithDenyingSecurityManager() {
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
                if (perm instanceof RuntimePermission && "shutdownHooks".equals(perm.getName())) {
                    throw new SecurityException("Permission denied: " + perm.getName());
                }
                // allow other permissions
            }

            @Override
            public void checkExit(int status) {
                // allow exit
            }
        };

        SecurityManager old = System.getSecurityManager();
        try {
            System.setSecurityManager(sm);
            Runtime rt = Runtime.getRuntime();
            Thread hook = new Thread();
            try {
                rt.removeShutdownHook(hook);
                throw new RuntimeException("Expected SecurityException not thrown");
            } catch (SecurityException se) {
                // expected
                String msg = se.getMessage();
                if (msg == null || !msg.contains("shutdownHooks")) {
                    throw new RuntimeException("SecurityException message unexpected: " + msg);
                }
            }
        } finally {
            System.setSecurityManager(old);
        }
    }
}
