/*
 * @test
 * @summary Test System.registerShutdownHook method behavior
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) {
        final int testSlot = 1;
        final boolean registerShutdownInProgress = false;
        final boolean[] hookRun = {false};

        Runnable hook = new Runnable() {
            public void run() {
                hookRun[0] = true;
            }
        };

        System system = new System() {
            // Anonymous subclass to access instance method
            // (System.registerShutdownHook is instance method in excerpt)
        };

        // Register the shutdown hook
        system.registerShutdownHook(testSlot, registerShutdownInProgress, hook);

        // Since the hook runs only on shutdown, we cannot test execution here,
        // but we can test that no exception is thrown and method accepts the hook.

        // Test with registerShutdownInProgress = true
        system.registerShutdownHook(testSlot, true, hook);

        // Test with null hook (if allowed by code, no exception expected from excerpt)
        system.registerShutdownHook(testSlot, false, null);

        // If no exceptions thrown, test passes
        System.out.println("Test passed");
    }
}
