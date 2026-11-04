/*
 * @test
 * @summary Test Runtime.removeShutdownHook(Thread) for security and null handling
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        // Test: removeShutdownHook with null argument
        // Expected: method returns false (no exception thrown by current code)
        boolean resultNull = runtime.removeShutdownHook(null);
        if (resultNull) {
            throw new Error("removeShutdownHook(null) should return false or not true");
        }

        // Test: removeShutdownHook with a dummy thread not started
        Thread dummyHook = new Thread();
        boolean resultDummy = runtime.removeShutdownHook(dummyHook);
        // The hook was never added, so removal should return false
        if (resultDummy) {
            throw new Error("removeShutdownHook(dummyHook) should return false for unregistered hook");
        }

        // Test: SecurityManager check - if a SecurityManager is installed that denies permission,
        // removeShutdownHook should throw SecurityException
        SecurityManager originalSM = System.getSecurityManager();
        try {
            System.setSecurityManager(new SecurityManager() {
                @Override
                public void checkPermission(java.security.Permission perm) {
                    if ("shutdownHooks".equals(perm.getName())) {
                        throw new SecurityException("Permission denied");
                    }
                }
            });
            boolean threw = false;
            try {
                runtime.removeShutdownHook(dummyHook);
            } catch (SecurityException se) {
                threw = true;
            }
            if (!threw) {
                throw new Error("Expected SecurityException when SecurityManager denies shutdownHooks permission");
            }
        } finally {
            System.setSecurityManager(originalSM);
        }

        System.out.println("RuntimeWitupTest passed");
    }
}
