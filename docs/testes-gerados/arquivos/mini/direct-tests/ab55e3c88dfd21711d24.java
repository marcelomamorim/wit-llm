/*
 * @test
 * @summary Test CountDownLatch countDown method behavior
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {
    public static void main(String[] args) throws Exception {
        testCountDownDecrementsCount();
        testCountDownDoesNotGoBelowZero();
    }

    static void testCountDownDecrementsCount() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        latch.countDown();
        // After one countDown, await should not return immediately
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    latch.await();
                    throw new RuntimeException("await returned too early");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        t.start();
        Thread.sleep(100); // Give thread time to block
        if (!t.isAlive()) {
            throw new RuntimeException("Thread should be blocked on await");
        }
        latch.countDown(); // count reaches zero
        t.join(1000);
        if (t.isAlive()) {
            throw new RuntimeException("Thread did not unblock after countDown to zero");
        }
    }

    static void testCountDownDoesNotGoBelowZero() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        latch.countDown(); // count reaches zero
        latch.countDown(); // extra countDown should have no effect
        // await should return immediately
        latch.await();
    }
}
