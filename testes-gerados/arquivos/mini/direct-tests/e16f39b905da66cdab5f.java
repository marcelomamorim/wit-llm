/*
 * @test
 * @summary Test constructor of CyclicBarrier with parties and barrierAction
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) {
        testValidPartiesAndNullAction();
        testValidPartiesAndNonNullAction();
        testZeroPartiesThrows();
        testNegativePartiesThrows();
    }

    private static void testValidPartiesAndNullAction() {
        int parties = 3;
        CyclicBarrier barrier = new CyclicBarrier(parties, null);
        if (barrier == null) throw new AssertionError("CyclicBarrier instance is null");
    }

    private static void testValidPartiesAndNonNullAction() {
        int parties = 2;
        Runnable action = new Runnable() {
            public void run() {
                // no-op
            }
        };
        CyclicBarrier barrier = new CyclicBarrier(parties, action);
        if (barrier == null) throw new AssertionError("CyclicBarrier instance is null");
    }

    private static void testZeroPartiesThrows() {
        try {
            new CyclicBarrier(0, null);
            throw new AssertionError("Expected IllegalArgumentException for zero parties");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testNegativePartiesThrows() {
        try {
            new CyclicBarrier(-1, null);
            throw new AssertionError("Expected IllegalArgumentException for negative parties");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
