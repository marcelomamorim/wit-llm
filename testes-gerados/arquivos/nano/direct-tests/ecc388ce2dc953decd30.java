/* @test    @summary Test ThreadLocalRandom.setSeed method behavior    @run main ThreadLocalRandomWitupTest */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testSetSeedThrowsException();
    }

    public static void testSetSeedThrowsException() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        boolean exceptionThrown = false;
        try {
            random.setSeed(12345L);
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
