/* @test    @summary Test the put method of LinkedBlockingDeque for basic functionality and blocking behavior    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testPutAddsElement();
        testPutBlocksWhenFull();
    }

    private static void testPutAddsElement() throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(2);
        deque.put(1);
        deque.put(2);
        assert deque.size() == 2 : "Deque should contain 2 elements";
        assert deque.peekFirst() == 1 : "First element should be 1";
        assert deque.peekLast() == 2 : "Last element should be 2";
    }

    private static void testPutBlocksWhenFull() throws InterruptedException {
        final LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
        deque.put(1);
        Thread t = new Thread(() -> {
            try {
                deque.put(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        Thread.sleep(100); // Ensure the thread attempts to put
        assert t.isAlive() : "Thread should be blocked on put() when deque is full";
        deque.take(); // Remove element to unblock
        t.join(1000);
        assert deque.size() == 1 : "Deque should have one element after removal";
        assert deque.peekFirst() == 2 : "Remaining element should be 2";
    }
}
