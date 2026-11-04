/*
 * @test
 * @summary Test LinkedBlockingDeque.toArray() method for correct behavior and locking
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test toArray on empty deque returns empty array
        Object[] emptyArray = deque.toArray();
        if (emptyArray == null) {
            throw new AssertionError("toArray() returned null on empty deque");
        }
        if (emptyArray.length != 0) {
            throw new AssertionError("toArray() returned non-empty array on empty deque");
        }

        // Add elements and test toArray returns them in correct order
        deque.add("first");
        deque.add("second");
        deque.add("third");

        Object[] arr = deque.toArray();
        if (arr == null) {
            throw new AssertionError("toArray() returned null on non-empty deque");
        }
        if (arr.length != 3) {
            throw new AssertionError("toArray() returned array of wrong length: " + arr.length);
        }
        if (!"first".equals(arr[0]) || !"second".equals(arr[1]) || !"third".equals(arr[2])) {
            throw new AssertionError("toArray() returned elements in wrong order or wrong elements");
        }

        // Test that toArray locks and unlocks properly by checking no exceptions and normal behavior
        // (No direct way to test lock state externally, so rely on no deadlock or exception)

        // Test concurrent modification scenario: start a thread that adds elements while toArray is called
        final LinkedBlockingDeque<String> concurrentDeque = new LinkedBlockingDeque<>();
        concurrentDeque.add("one");
        concurrentDeque.add("two");

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50); // wait a bit
                    concurrentDeque.add("three");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        t.start();

        Object[] concurrentArray = concurrentDeque.toArray();
        // The array should contain at least the first two elements
        if (concurrentArray.length < 2) {
            throw new AssertionError("toArray() returned array with less elements than expected during concurrent modification");
        }

        t.join();

        // After thread finished, toArray should reflect all three elements
        Object[] afterArray = concurrentDeque.toArray();
        if (afterArray.length != 3) {
            throw new AssertionError("toArray() after concurrent modification returned wrong length: " + afterArray.length);
        }

        System.out.println("All LinkedBlockingDeque.toArray() tests passed.");
    }
}
