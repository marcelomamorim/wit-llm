/*
 * @test
 * @summary Test System.registerShutdownHook method for argument validation and normal behavior
 * @run main SystemWitupTest
 */

public class SystemWitupTest {
    public static void main(String[] args) {
        testValidSlot();
        testInvalidSlotNegative();
        testInvalidSlotTooLarge();
    }

    private static void testValidSlot() {
        // Use a valid slot in range [0,9]
        final int validSlot = 5;
        final boolean registerShutdownInProgress = false;
        final Runnable hook = new Runnable() {
            public void run() {
                // no-op
            }
        };
        // The call should not throw any exception
        System system = new System() {
            // Override to call the instance method if needed
            // But System.registerShutdownHook is static in reality, here we simulate call
            public void registerShutdownHook(int slot, boolean registerShutdownInProgress, Runnable hook) {
                // Delegate to Shutdown.add internally, but here just call the static method
                System.registerShutdownHook(slot, registerShutdownInProgress, hook);
            }
        };
        // Since the method is instance in excerpt but actually static, call static directly
        System.registerShutdownHook(validSlot, registerShutdownInProgress, hook);
    }

    private static void testInvalidSlotNegative() {
        final int invalidSlot = -1;
        final boolean registerShutdownInProgress = false;
        final Runnable hook = new Runnable() {
            public void run() {
                // no-op
            }
        };
        try {
            System.registerShutdownHook(invalidSlot, registerShutdownInProgress, hook);
            throw new RuntimeException("Expected IllegalArgumentException for slot < 0");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testInvalidSlotTooLarge() {
        final int invalidSlot = 10;
        final boolean registerShutdownInProgress = false;
        final Runnable hook = new Runnable() {
            public void run() {
                // no-op
            }
        };
        try {
            System.registerShutdownHook(invalidSlot, registerShutdownInProgress, hook);
            throw new RuntimeException("Expected IllegalArgumentException for slot >= 10");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
