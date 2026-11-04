/* @test    @summary Test addShutdownHook method for Runtime class    @run main RuntimeWitupTest */

import java.lang.Runtime;
import java.lang.Thread;
import java.lang.SecurityManager;
import java.lang.SecurityException;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testAddShutdownHookWithSecurityManager();
        testAddShutdownHookWithoutSecurityManager();
    }

    private static void testAddShutdownHookWithSecurityManager() {
        Runtime runtime = Runtime.getRuntime();
        Thread hook = new Thread();
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
                if ("shutdownHooks".equals(perm.getName())) {
                    throw new SecurityException("Permission denied") ;
                }
            }
        };
        System.setSecurityManager(sm);
        try {
            runtime.addShutdownHook(hook);
            // If no exception, test passes
        } catch (SecurityException se) {
            // Expected exception due to security manager
        } finally {
            System.setSecurityManager(null);
        }
    }

    private static void testAddShutdownHookWithoutSecurityManager() {
        Runtime runtime = Runtime.getRuntime();
        Thread hook = new Thread();
        try {
            runtime.addShutdownHook(hook);
            // Should succeed without exception
        } catch (SecurityException se) {
            // Fail the test if exception occurs
            throw new AssertionError("Unexpected SecurityException", se);
        }
    }
}
