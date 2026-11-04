/*
 * @test
 * @summary Test LinkedBlockingDeque.takeLast() blocking and unlocking behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test that takeLast blocks when empty and returns element after insertion
        final AtomicBoolean took = new AtomicBoolean(false);

        Thread taker = new Thread(new Runnable() {
            public void run() {
                try {
                    String result = deque.takeLast();
                    if ("test".equals(result)) {
                        took.set(true);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        taker.start();

        // Sleep briefly to ensure taker thread is waiting
        Thread.sleep(100);

        // At this point, taker should be blocked waiting for element
        if (took.get()) {
            throw new RuntimeException("takeLast returned prematurely");
        }

        // Insert element at the end
        deque.putLast("test");

        // Wait for taker to finish
        taker.join(1000);

        if (!took.get()) {
            throw new RuntimeException("takeLast did not return the expected element");
        }

        // Test that takeLast returns immediately when element is present
        deque.putLast("immediate");
        String val = deque.takeLast();
        if (!"immediate".equals(val)) {
            throw new RuntimeException("takeLast did not return the expected immediate element");
        }
    }
}
