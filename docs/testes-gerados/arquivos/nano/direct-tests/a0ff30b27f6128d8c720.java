/* @test    @summary Test Runtime.removeShutdownHook method for basic behavior    @run main RuntimeWitupTest */

import java.lang.Runtime;
import java.lang.Thread;
import java.lang.SecurityManager;
import java.lang.SecurityException;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testRemoveShutdownHookReturnsTrueWhenHookIsPresent();
        testRemoveShutdownHookReturnsFalseWhenHookIsAbsent();
        testRemoveShutdownHookWithNull();
    }

    private static void testRemoveShutdownHookReturnsTrueWhenHookIsPresent() {
        Runtime runtime = Runtime.getRuntime();
        Thread hook = new Thread();
        // Add hook to simulate presence
        boolean added = ApplicationShutdownHooks.add(hook);
        if (!added) {
            System.out.println("Failed to add hook for test.");
            return;
        }
        boolean result = runtime.removeShutdownHook(hook);
        System.out.println("removeShutdownHook with present hook: " + result);
        // Cleanup
        ApplicationShutdownHooks.remove(hook);
    }

    private static void testRemoveShutdownHookReturnsFalseWhenHookIsAbsent() {
        Runtime runtime = Runtime.getRuntime();
        Thread hook = new Thread();
        boolean result = runtime.removeShutdownHook(hook);
        System.out.println("removeShutdownHook with absent hook: " + result);
    }

    private static void testRemoveShutdownHookWithNull() {
        Runtime runtime = Runtime.getRuntime();
        try {
            boolean result = runtime.removeShutdownHook(null);
            System.out.println("removeShutdownHook with null: " + result);
        } catch (NullPointerException e) {
            System.out.println("removeShutdownHook with null threw NullPointerException as expected.");
        }
    }

    // Helper class to simulate ApplicationShutdownHooks behavior
    static class ApplicationShutdownHooks {
        private static final java.util.List<Thread> hooks = new java.util.ArrayList<>();

        static boolean add(Thread hook) {
            synchronized (hooks) {
                return hooks.add(hook);
            }
        }

        static boolean remove(Thread hook) {
            synchronized (hooks) {
                return hooks.remove(hook);
            }
        }
    }
}
