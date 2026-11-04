/*
 * @test
 * @summary Verifies CyclicBarrier(int parties) delegates to the public constructor, reports parties, returns arrival index 0 for a single party, and rejects non-positive parties
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(3);
        check(barrier.getParties() == 3, "getParties should return 3");
        check(barrier.getNumberWaiting() == 0, "no thread should be waiting initially");
        check(!barrier.isBroken(), "new barrier should not be broken");

        CyclicBarrier single = new CyclicBarrier(1);
        check(single.getParties() == 1, "single-party getParties should return 1");
        int index = single.await();
        check(index == 0, "single party await should return arrival index 0");
        check(single.getNumberWaiting() == 0, "no waiters after single party await");
        check(!single.isBroken(), "barrier should not be broken after normal await");

        index = single.await();
        check(index == 0, "barrier should be reusable across cycles");

        try {
            new CyclicBarrier(0);
            throw new RuntimeException("parties=0 should be rejected");
        } catch (IllegalArgumentException expected) {
        }

        try {
            new CyclicBarrier(-1);
            throw new RuntimeException("parties=-1 should be rejected");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
