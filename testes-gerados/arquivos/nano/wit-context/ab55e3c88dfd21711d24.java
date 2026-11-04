/*
 * @test
 * @summary Test for CountDownLatch.countDown() method behavior
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testCountDownDecrementsCount();
        testCountDownMultipleTimes();
        testCountDownWhenCountIsZero();
    }

    private static void testCountDownDecrementsCount() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        latch.countDown();
        if (latch.getCount() != 1) {
            throw new AssertionError("Expected count to be 1 after one countDown, but was " + latch.getCount());
        }
        latch.countDown();
        if (latch.getCount() != 0) {
            throw new AssertionError("Expected count to be 0 after two countDowns, but was " + latch.getCount());
        }
    }

    private static void testCountDownMultipleTimes() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        latch.countDown();
        latch.countDown(); // should have no effect, count remains at 0
        if (latch.getCount() != 0) {
            throw new AssertionError("Expected count to remain 0 after extra countDown, but was " + latch.getCount());
        }
    }

    private static void testCountDownWhenCountIsZero() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(0);
        latch.countDown(); // should have no effect, count remains at 0
        if (latch.getCount() != 0) {
            throw new AssertionError("Expected count to remain 0 after countDown on zero, but was " + latch.getCount());
        }
    }
}
