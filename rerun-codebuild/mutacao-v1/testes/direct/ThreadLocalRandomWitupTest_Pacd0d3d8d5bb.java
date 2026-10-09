/* @test    @summary Test that setSeed throws UnsupportedOperationException after initialization    @run main ThreadLocalRandomWitupTest_Pacd0d3d8d5bb */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest_Pacd0d3d8d5bb {
    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        try {
            random.setSeed(12345L);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
