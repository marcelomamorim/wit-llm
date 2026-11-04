/*
 * @test
 * @summary Test for LinkedBlockingDeque.takeLast() method, focusing on normal behavior and exception handling.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeLastReturnsElement();
        testTakeLastBlocksAndUnblocks();
        testTakeLastThrowsInterruptedException();
    }

    private static void testTakeLastReturnsElement() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element");
        String result = deque.takeLast();
        if (!"element".equals(result)) {
            throw new AssertionError("Expected 'element', got: " + result);
        }
    }

    private static void testTakeLastBlocksAndUnblocks() throws Exception {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Thread t = new Thread(() -> {
            try {
                String val = deque.takeLast();
                if (!"delayed".equals(val)) {
                    throw new AssertionError("Expected 'delayed', got: " + val);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();
        Thread.sleep(100); // ensure thread is waiting
        deque.add("delayed");
        t.join(1000);
        if (t.isAlive()) {
            throw new AssertionError("Thread did not complete after element was added");
        }
    }

    private static void testTakeLastThrowsInterruptedException() throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Thread t = new Thread(() -> {
            try {
                deque.takeLast();
            } catch (InterruptedException e) {
                // expected
            }
        });
        t.start();
        Thread.sleep(100);
        t.interrupt();
        t.join(1000);
        if (t.isAlive()) {
            throw new AssertionError("Thread did not terminate after interruption");
        }
    }
}
