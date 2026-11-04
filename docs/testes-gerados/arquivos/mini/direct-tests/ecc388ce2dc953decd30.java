/*
 * @test
 * @summary Test ThreadLocalRandom.setSeed behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
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
