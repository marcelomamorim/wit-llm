/*
 * @test
 * @summary Verifies CyclicBarrier(int, Runnable) constructor validation and observable state
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroPartiesRejected() {
        boolean thrown = false;
        try {
            new CyclicBarrier(0, () -> { });
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "parties=0 must throw IllegalArgumentException");
    }

    static void testNegativePartiesRejected() {
        boolean thrown = false;
        try {
            new CyclicBarrier(-1, () -> { });
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "parties=-1 must throw IllegalArgumentException");
    }

    static void testPartiesStored() {
        CyclicBarrier b = new CyclicBarrier(3, () -> { });
        check(b.getParties() == 3, "getParties() should return 3");
        check(!b.isBroken(), "new barrier must not be broken");
        check(b.getNumberWaiting() == 0, "no parties should be waiting initially");
    }

    static void testNullActionAllowed() throws Exception {
        CyclicBarrier b = new CyclicBarrier(1, null);
        check(b.getParties() == 1, "getParties() should return 1");
        int index = b.await();
        check(index == 0, "single party await should return index 0");
    }

    static void testBarrierActionRunsOnce() throws Exception {
        final int[] runs = new int[1];
        CyclicBarrier b = new CyclicBarrier(1, () -> runs[0]++);
        int index = b.await();
        check(index == 0, "single party await should return index 0");
        check(runs[0] == 1, "barrier action should run exactly once");
    }

    public static void main(String[] args) throws Exception {
        testZeroPartiesRejected();
        testNegativePartiesRejected();
        testPartiesStored();
        testNullActionAllowed();
        testBarrierActionRunsOnce();
    }
}
