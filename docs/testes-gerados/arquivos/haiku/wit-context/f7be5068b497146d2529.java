/*
 * @test
 * @summary CountDownLatch constructor rejects negative counts and accepts zero
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchWitupTest {

    public static void main(String[] args) throws Exception {
        checkNegativeRejected(-1);
        checkNegativeRejected(Integer.MIN_VALUE);
        checkZeroCount();
        checkCountDownBehavior();
        checkTimedAwaitTimesOut();
    }

    static void checkNegativeRejected(int count) {
        try {
            new CountDownLatch(count);
            throw new RuntimeException("no IllegalArgumentException thrown");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void checkZeroCount() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(0);
        if (latch.getCount() != 0) {
            throw new RuntimeException("initial count should be 0");
        }
        latch.await();
        if (!latch.await(1, TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("timed await on zero count should return true");
        }
    }

    static void checkCountDownBehavior() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        if (latch.getCount() != 2) {
            throw new RuntimeException("initial count should be 2");
        }
        latch.countDown();
        if (latch.getCount() != 1) {
            throw new RuntimeException("count should be 1");
        }
        latch.countDown();
        if (latch.getCount() != 0) {
            throw new RuntimeException("count should be 0");
        }
        latch.countDown();
        if (latch.getCount() != 0) {
            throw new RuntimeException("count should stay at 0");
        }
        if (!latch.await(1, TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("await after release should return true");
        }
    }

    static void checkTimedAwaitTimesOut() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        if (latch.await(1, TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("timed await should time out with count 1");
        }
        if (latch.getCount() != 1) {
            throw new RuntimeException("count should remain 1");
        }
    }
}
