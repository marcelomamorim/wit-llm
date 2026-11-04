/*
 * @test
 * @summary Test the await() method of CountDownLatch for normal and interrupted behavior.
 * @run main CountDownLatchWitupTest
 */

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWitupTest {

    public static void main(String[] args) throws Exception {
        testAwaitNormal();
        testAwaitInterrupted();
    }

    private static void testAwaitNormal() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            try {
                latch.await();
                System.out.println("Await completed without interruption.");
            } catch (InterruptedException e) {
                System.out.println("Unexpected interruption.");
            }
        });
        t.start();
        Thread.sleep(100); // ensure thread is waiting
        latch.countDown();
        t.join();
    }

    private static void testAwaitInterrupted() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(() -> {
            try {
                latch.await();
                System.out.println("Await completed without interruption.");
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted as expected.");
            }
        });
        t.start();
        Thread.sleep(100); // ensure thread is waiting
        t.interrupt();
        latch.countDown();
        t.join();
    }
}
