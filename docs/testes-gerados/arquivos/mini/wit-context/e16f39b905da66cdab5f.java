/*
 * @test
 * @summary Test constructor CyclicBarrier(int, Runnable) for valid and invalid parties
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) {
        testValidParties();
        testInvalidParties();
    }

    private static void testValidParties() {
        // Test with positive parties and null barrierAction
        CyclicBarrier barrier1 = new CyclicBarrier(1, null);
        if (barrier1 == null) throw new AssertionError("CyclicBarrier instance is null");

        // Test with positive parties and a simple barrierAction
        Runnable action = new Runnable() {
            public void run() {
                // no-op
            }
        };
        CyclicBarrier barrier2 = new CyclicBarrier(3, action);
        if (barrier2 == null) throw new AssertionError("CyclicBarrier instance is null");
    }

    private static void testInvalidParties() {
        // Test with zero parties - expect IllegalArgumentException
        try {
            new CyclicBarrier(0, null);
            throw new AssertionError("Expected IllegalArgumentException for parties=0");
        } catch (IllegalArgumentException expected) {
        }

        // Test with negative parties - expect IllegalArgumentException
        try {
            new CyclicBarrier(-1, null);
            throw new AssertionError("Expected IllegalArgumentException for parties=-1");
        } catch (IllegalArgumentException expected) {
        }
    }
}
