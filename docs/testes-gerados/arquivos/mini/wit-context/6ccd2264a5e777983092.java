/*
 * @test
 * @summary Test CyclicBarrier constructor with invalid and valid parties argument
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) {
        testConstructorWithInvalidParties();
        testConstructorWithValidParties();
    }

    private static void testConstructorWithInvalidParties() {
        int[] invalidParties = {0, -1, -10};
        for (int parties : invalidParties) {
            try {
                new CyclicBarrier(parties);
                throw new RuntimeException("Expected IllegalArgumentException for parties=" + parties);
            } catch (IllegalArgumentException expected) {
                // Expected exception
            }
        }
    }

    private static void testConstructorWithValidParties() {
        int[] validParties = {1, 2, 10};
        for (int parties : validParties) {
            CyclicBarrier barrier = new CyclicBarrier(parties);
            if (barrier == null) {
                throw new RuntimeException("CyclicBarrier constructor returned null for parties=" + parties);
            }
        }
    }
}
