/*
 * @test
 * @summary Test the put method of LinkedBlockingQueue for basic functionality and edge cases.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testPutNormal();
        testPutNull();
        testPutBlockingBehavior();
    }

    static void testPutNormal() throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        queue.put("element1");
        queue.put("element2");
        assert queue.size() == 2 : "Queue should have 2 elements";
        assert queue.peek().equals("element1") : "First element should be 'element1'";
    }

    static void testPutNull() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.put(null);
            assert false : "Putting null should throw NullPointerException";
        } catch (NullPointerException expected) {
            // expected exception
        }
    }

    static void testPutBlockingBehavior() throws InterruptedException {
        final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
        queue.put("first");
        Thread t = new Thread(() -> {
            try {
                queue.put("second");
                assert false : "This should block until space is available";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        TimeUnit.MILLISECONDS.sleep(100); // ensure thread is blocked
        assert queue.size() == 1 : "Queue should be full";
        queue.take(); // remove element to unblock
        t.join(1000);
        assert queue.size() == 1 : "Queue should have 1 element after unblock";
        assert queue.peek().equals("second") : "Remaining element should be 'second'";
    }
}
