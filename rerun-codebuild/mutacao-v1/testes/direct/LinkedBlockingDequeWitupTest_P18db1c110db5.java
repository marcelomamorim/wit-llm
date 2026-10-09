/* @test    @summary Test putLast method of LinkedBlockingDeque for correct blocking behavior    @run main LinkedBlockingDequeWitupTest_P18db1c110db5 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeWitupTest_P18db1c110db5 {

    public static void main(String[] args) throws InterruptedException {
        testPutLastBlocksAndCompletes();
    }

    private static void testPutLastBlocksAndCompletes() throws InterruptedException {
        final LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
        final Thread producer = new Thread(() -> {
            try {
                deque.putLast(42);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.start();
        // Wait briefly to ensure putLast is blocking if deque is full
        TimeUnit.MILLISECONDS.sleep(50);
        if (!producer.isAlive()) {
            throw new AssertionError("Producer thread terminated prematurely, expected to block on putLast.");
        }
        // Remove element to free space
        Integer removed = deque.takeFirst();
        if (removed == null || removed != 42) {
            throw new AssertionError("Expected to remove 42 from deque, but got: " + removed);
        }
        // Wait for producer to finish
        producer.join(1000);
        if (producer.isAlive()) {
            throw new AssertionError("Producer thread did not complete after space was freed.");
        }
        // Verify element was added
        Integer element = deque.takeFirst();
        if (element == null || element != 42) {
            throw new AssertionError("Expected to retrieve 42 after putLast, but got: " + element);
        }
    }
}
