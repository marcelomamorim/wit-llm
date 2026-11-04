/* @test    @summary Test the take() method of LinkedBlockingDeque for correct blocking behavior and return value.    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testTakeReturnsElement();
        testTakeBlocksUntilElementAvailable();
    }

    private static void testTakeReturnsElement() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element");
        String result = deque.take();
        if (!"element".equals(result)) {
            throw new AssertionError("Expected 'element', but got: " + result);
        }
    }

    private static void testTakeBlocksUntilElementAvailable() throws InterruptedException {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Thread t = new Thread(() -> {
            try {
                String val = deque.take();
                if (!"delayed".equals(val)) {
                    throw new AssertionError("Expected 'delayed', but got: " + val);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        Thread.sleep(100); // ensure thread is waiting on take()
        deque.add("delayed");
        t.join();
    }
}
