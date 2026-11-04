/*
 * @test
 * @summary Test CountDownLatch countDown method for correct decrement and thread release
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testCountDownDecrementsCount();
        testCountDownMultipleTimes();
        testCountDownAfterAwait();
    }

    static void testCountDownDecrementsCount() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        latch.countDown();
        assert latch.getCount() == 1 : "Count should be 1 after one countDown";
        latch.countDown();
        assert latch.getCount() == 0 : "Count should be 0 after two countDowns";
    }

    static void testCountDownMultipleTimes() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);
        latch.countDown();
        latch.countDown();
        latch.countDown();
        assert latch.getCount() == 0 : "Count should be 0 after three countDowns";
    }

    static void testCountDownAfterAwait() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            try {
                latch.await();
                // After await, countDown should have been called
                assert latch.getCount() == 0 : "Count should be 0 after countDown and await";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        Thread.sleep(50); // ensure thread is waiting
        latch.countDown();
        t.join();
    }
}
