/*
 * @test
 * @summary Tests the put method of ArrayBlockingQueue for basic functionality.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testPutBasic();
        testPutBlocksWhenFull();
        testPutResumesAfterTake();
    }

    static void testPutBasic() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.put(1);
        queue.put(2);
        assert queue.size() == 2 : "Queue should have 2 elements";
    }

    static void testPutBlocksWhenFull() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        Thread producer = new Thread(() -> {
            try {
                queue.put(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        queue.put(5); // fill the queue
        producer.start();
        TimeUnit.MILLISECONDS.sleep(100); // ensure producer is blocked
        assert producer.isAlive() : "Producer thread should be blocked";
        queue.take(); // remove element to unblock
        producer.join(1000);
        assert !producer.isAlive() : "Producer thread should have resumed";
        assert queue.size() == 1 : "Queue should have 1 element after take";
    }

    static void testPutResumesAfterTake() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        queue.put(42);
        Thread consumer = new Thread(() -> {
            try {
                Integer val = queue.take();
                assert val == 42 : "Expected value 42";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        consumer.join(1000);
        assert !consumer.isAlive() : "Consumer thread should have finished";
        assert queue.isEmpty() : "Queue should be empty after take";
    }
}
