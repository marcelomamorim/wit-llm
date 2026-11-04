/*
 * @test
 * @summary Verifies that CyclicBarrier(int parties) rejects non-positive party counts and accepts positive ones
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) {
        int[] invalid = {0, -1, Integer.MIN_VALUE};
        for (int p : invalid) {
            try {
                new CyclicBarrier(p);
                throw new AssertionError("Expected IllegalArgumentException for parties=" + p);
            } catch (IllegalArgumentException expected) {
            }
        }
        new CyclicBarrier(1);
        new CyclicBarrier(2);
    }
}
