/* @test    @summary Test CyclicBarrier constructor with valid parameters    @run main CyclicBarrierWitupTest */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {

    public static void main(String[] args) {
        testValidConstructorWithRunnable();
        testInvalidConstructorZeroParties();
        testInvalidConstructorNegativeParties();
    }

    static void testValidConstructorWithRunnable() {
        Runnable dummyRunnable = new Runnable() {
            public void run() {
                // no-op
            }
        };
        CyclicBarrier barrier = new CyclicBarrier(3, dummyRunnable);
        assert barrier != null : "Barrier should be created with valid parameters";
    }

    static void testInvalidConstructorZeroParties() {
        try {
            new CyclicBarrier(0, null);
            assert false : "Expected IllegalArgumentException for zero parties";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidConstructorNegativeParties() {
        try {
            new CyclicBarrier(-1, null);
            assert false : "Expected IllegalArgumentException for negative parties";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
