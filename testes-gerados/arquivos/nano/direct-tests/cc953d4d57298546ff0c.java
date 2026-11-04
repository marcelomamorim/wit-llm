/* @test    @summary Test PriorityBlockingQueue.take() method for blocking behavior and correct element retrieval    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeBlocksAndReturnsElement();
    }

    private static void testTakeBlocksAndReturnsElement() throws Exception {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(100); // Delay to ensure take() blocks
                queue.put(42);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        long startTime = System.nanoTime();
        Integer result = queue.take();
        long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        if (result != null && result == 42 && durationMs >= 100) {
            System.out.println("PASS: take() blocked until element was available and returned correct element.");
        } else {
            System.out.println("FAIL: take() did not behave as expected. Result: " + result + ", Duration: " + durationMs + " ms");
        }
        producer.join();
    }
}
