/* @test    @summary Test for ThreadLocalRandom.setSeed(long) method to verify exception behavior.    @run main ThreadLocalRandomWitupTest_Pacd0d3d8d5bb */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_Pacd0d3d8d5bb {
    public static void main(String[] args) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        boolean exceptionThrown = false;
        try {
            r.setSeed(12345L);
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
