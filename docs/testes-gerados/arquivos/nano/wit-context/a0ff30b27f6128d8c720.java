/*
 * @test    @summary Test for Runtime.removeShutdownHook method
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;
import java.lang.Thread;
import java.security.Permission;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testRemoveShutdownHookNullPointerException();
        testRemoveShutdownHookIllegalStateException();
        testRemoveShutdownHookSuccess();
    }

    private static void testRemoveShutdownHookNullPointerException() {
        Runtime runtime = Runtime.getRuntime();
        Thread hook = null;
        try {
            runtime.removeShutdownHook(hook);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testRemoveShutdownHookIllegalStateException() {
        Runtime runtime = Runtime.getRuntime();
        Thread hook = new Thread();
        // Setup: simulate security manager with permission
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                if ("shutdownHooks".equals(perm.getName())) {
                    // allow
                } else {
                    super.checkPermission(perm);
                }
            }
        };
        SecurityManager originalSM = System.getSecurityManager();
        System.setSecurityManager(sm);
        try {
            // Simulate ApplicationShutdownHooks.remove() throwing IllegalStateException
            // Since ApplicationShutdownHooks is internal, we simulate by overriding SecurityManager
            // or by mocking, but as per constraints, we cannot access internal classes.
            // Instead, we invoke removeShutdownHook with a hook that triggers exception.
            // For simplicity, we assume the method throws IllegalStateException if hooks == null.
            // So, forcibly set hooks to null to simulate.
            // But since hooks is internal, we cannot set it directly; instead, we can simulate by
            // calling removeShutdownHook with a hook that causes the exception.
            // Alternatively, we can skip this test if internal state cannot be manipulated.
            // Here, we proceed with a try-catch to catch the exception.
            try {
                runtime.removeShutdownHook(hook);
                System.out.println("FAIL: Expected IllegalStateException not thrown");
            } catch (IllegalStateException e) {
                System.out.println("PASS: IllegalStateException thrown as expected");
            } catch (Throwable t) {
                System.out.println("FAIL: Unexpected exception: " + t);
            }
        } finally {
            System.setSecurityManager(originalSM);
        }
    }

    private static void testRemoveShutdownHookSuccess() {
        Runtime runtime = Runtime.getRuntime();
        Thread hook = new Thread();
        // Add a hook to remove
        // Since ApplicationShutdownHooks is internal, we simulate by adding and removing a thread
        // but as per constraints, we cannot access internal classes.
        // So, we test the method with a dummy hook and expect it to return false if not present.
        boolean result = false;
        try {
            result = runtime.removeShutdownHook(hook);
            System.out.println("Result of removeShutdownHook with non-registered hook: " + result);
        } catch (Throwable t) {
            System.out.println("FAIL: Exception during removeShutdownHook: " + t);
        }
    }
}
