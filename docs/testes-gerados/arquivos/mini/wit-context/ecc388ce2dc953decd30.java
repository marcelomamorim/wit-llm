/*
 * @test
 * @summary Test ThreadLocalRandom.setSeed(long) throws UnsupportedOperationException if initialized
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        boolean thrown = false;
        try {
            tlr.setSeed(123L);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown by setSeed");
        }
    }
}
