/* @test    @summary Test for ThreadLocalRandom.setSeed to verify exception behavior in initialized state    @run main ThreadLocalRandomWitupTest_Pd40aea5c61c4 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_Pd40aea5c61c4 {

    public static void main(String[] args) {
        testSetSeedWhenInitialized();
    }

    private static void testSetSeedWhenInitialized() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        // Simulate the initialized state by calling a method that would set it internally
        // Since the actual internal state is private, we rely on the known behavior: calling setSeed after initialization throws
        try {
            r.setSeed(12345L);
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Correct exception thrown for setSeed in initialized state.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
