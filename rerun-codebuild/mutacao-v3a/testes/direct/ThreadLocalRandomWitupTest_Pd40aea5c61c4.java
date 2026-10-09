/*
 * @test
 * @summary Test that ThreadLocalRandom.setSeed throws UnsupportedOperationException after initialization
 * @run main ThreadLocalRandomWitupTest_Pd40aea5c61c4
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_Pd40aea5c61c4 {

    public static void main(String[] args) {
        testSetSeedThrows();
    }

    public static void testSetSeedThrows() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        try {
            tlr.setSeed(12345L);
            System.out.println("FAIL: setSeed did not throw as expected.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: setSeed threw UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: setSeed threw unexpected exception: " + t);
        }
    }
}
