/* @test    @summary Test poll() method of PriorityBlockingQueue for correct element removal and thread safety    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.Arrays;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testPollReturnsAndRemovesSmallest();
        testPollOnEmptyReturnsNull();
        testPollIsThreadSafe();
    }

    private static void testPollReturnsAndRemovesSmallest() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>(Arrays.asList(5, 1, 3));
        Integer polled = pq.poll();
        assert polled != null && polled.equals(1) : "Expected poll() to return 1";
        assert pq.size() == 2 : "Expected size 2 after poll";
        assert pq.peek() != null && pq.peek().equals(3) : "Expected next element to be 3";
    }

    private static void testPollOnEmptyReturnsNull() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        Integer result = pq.poll();
        assert result == null : "Expected poll() on empty queue to return null";
    }

    private static void testPollIsThreadSafe() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                pq.offer(i);
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                pq.offer(i + 1000);
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Integer prev = null;
        while (!pq.isEmpty()) {
            Integer current = pq.poll();
            if (prev != null) {
                assert prev <= current : "Queue is not ordered properly";
            }
            prev = current;
        }
    }
}
