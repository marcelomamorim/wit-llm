/*
 * @test    @summary Test takeFirst() method of LinkedBlockingDeque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testTakeFirstBlocksAndReturnsElement();
        testTakeFirstReturnsElementWhenAvailable();
    }

    static void testTakeFirstBlocksAndReturnsElement() throws InterruptedException {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Thread t = new Thread(() -> {
            try {
                String result = deque.takeFirst();
                if (!"element".equals(result)) {
                    throw new AssertionError("Expected 'element', got: " + result);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        // Ensure the thread is waiting on takeFirst()
        Thread.sleep(100);
        deque.offer("element");
        t.join(1000);
        if (t.isAlive()) {
            throw new AssertionError("Thread did not complete after offering element");
        }
    }

    static void testTakeFirstReturnsElementWhenAvailable() throws InterruptedException {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offer("test");
        String result = deque.takeFirst();
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', got: " + result);
        }
    }
}
