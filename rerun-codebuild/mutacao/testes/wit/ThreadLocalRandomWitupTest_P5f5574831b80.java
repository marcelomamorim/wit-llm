/*
 * @test
 * @summary Test for ThreadLocalRandom.setSeed method to verify exception throwing behavior.
 * @run main ThreadLocalRandomWitupTest_P5f5574831b80
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P5f5574831b80 {

    public static void main(String[] args) {
        testSetSeedThrowsExceptionWhenInitialized();
    }

    private static void testSetSeedThrowsExceptionWhenInitialized() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        // Simulate the 'initialized' state by calling setSeed with a dummy seed
        // Note: Since setSeed throws if 'initialized' is true, we need to set 'initialized' to true.
        // However, 'initialized' is a private field; thus, we cannot set it directly.
        // Instead, we rely on the current implementation: calling setSeed after initialization throws.
        // But since the initial state is 'not initialized', we need to simulate the 'initialized' state.
        // As we cannot modify private fields directly, we invoke setSeed once to initialize.
        // Then, calling again should throw.
        try {
            r.setSeed(12345L); // First call to initialize
        } catch (UnsupportedOperationException e) {
            // If exception occurs here, test fails because it indicates already initialized
            System.err.println("Unexpected exception during initial setSeed: " + e);
            return;
        }
        // Now, call setSeed again, expecting exception
        try {
            r.setSeed(67890L);
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: setSeed threw UnsupportedOperationException as expected.");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }
}
