/*
 * @test
 * @summary Test ThreadLocalRandom.setSeed behavior
 * @run main ThreadLocalRandomWitupTest_P03332e65757a
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_P03332e65757a {
    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        // The setSeed method throws UnsupportedOperationException if called after initialization
        boolean threw = false;
        try {
            tlr.setSeed(12345L);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException from setSeed after initialization");
        }
    }
}
