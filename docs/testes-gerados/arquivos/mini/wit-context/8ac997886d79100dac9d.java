/*
 * @test
 * @summary Test LinkedBlockingDeque.take() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test take() blocks and retrieves the first element correctly
        Thread producer = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(100); // Delay to ensure take() blocks
                    deque.put("test");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        producer.start();

        String result = deque.take();

        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test' but got: " + result);
        }

        producer.join();

        // Test take() on empty deque blocks and then interrupted
        final Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    deque.take();
                    throw new AssertionError("take() should have been interrupted");
                } catch (InterruptedException expected) {
                    // Expected interruption
                }
            }
        });

        taker.start();
        Thread.sleep(100); // Ensure taker is blocked on take()
        taker.interrupt();
        taker.join();
    }
}
