/* @test    @summary Test the take() method of LinkedBlockingQueue for correct blocking and unblocking behavior    @run main LinkedBlockingQueueWitupTest */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeBlocksWhenEmpty();
        testTakeUnblocksWhenElementAvailable();
    }

    private static void testTakeBlocksWhenEmpty() throws Exception {
        final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Thread taker = new Thread(() -> {
            try {
                String result = queue.take();
                System.out.println("Unexpected unblocking with result: " + result);
            } catch (InterruptedException e) {
                System.out.println("take() was interrupted");
            }
        });
        taker.start();
        Thread.sleep(100); // Ensure the thread is waiting on take()
        if (taker.isAlive()) {
            System.out.println("take() is blocking as expected");
            queue.put("test");
            taker.join(1000);
            if (taker.isAlive()) {
                throw new AssertionError("take() did not unblock after put()");
            } else {
                System.out.println("take() unblocked successfully after put()");
            }
        } else {
            throw new AssertionError("take() thread did not block as expected");
        }
    }

    private static void testTakeUnblocksWhenElementAvailable() throws Exception {
        final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.put("element");
        String result = queue.take();
        if (!"element".equals(result)) {
            throw new AssertionError("Expected 'element', got: " + result);
        } else {
            System.out.println("take() returned the correct element");
        }
    }
}
