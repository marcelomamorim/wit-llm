/*
 * @test
 * @summary Witup-generated checks for CyclicBarrier constructor argument validation
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) throws Exception {
        checkInvalidParties(0);
        checkInvalidParties(-1);
        checkInvalidParties(Integer.MIN_VALUE);

        CyclicBarrier twoParties = new CyclicBarrier(2, null);
        if (twoParties.getParties() != 2) {
            throw new AssertionError("getParties() expected 2but was " + twoParties.getParties());
        }

        final int[] runs = {0};
        CyclicBarrier single = new CyclicBarrier(1, new Runnable() {
            public void run() {
                runs[0]++;
            }
        });
        int index = single.await();
        if (index != 0) {
            throw new AssertionError("await() expected index 0 but was " + index);
        }
        if (runs[0] != 1) {
            throw new AssertionError("barrier action expected 1 run but was " + runs[0]);
        }
    }

    static void checkInvalidParties(int parties) {
        try {
            new CyclicBarrier(parties, new Runnable() {
                public void run() {
                }
            });
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for parties=" + parties);
    }
}
