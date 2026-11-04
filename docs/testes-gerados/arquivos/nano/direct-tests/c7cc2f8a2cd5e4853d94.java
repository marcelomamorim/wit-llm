/*
 * @test
 * @summary Test the toArray() method of LinkedBlockingQueue for correctness and stability.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueue();
        testSingleElement();
        testMultipleElements();
        testNullElements();
        testConcurrentModification();
    }

    static void testEmptyQueue() {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        Object[] array = queue.toArray();
        assert array != null : "Array should not be null";
        assert array.length == 0 : "Array length should be 0 for empty queue";
    }

    static void testSingleElement() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("element");
        Object[] array = queue.toArray();
        assert array.length == 1 : "Array length should be 1";
        assert "element".equals(array[0]) : "Element should match";
    }

    static void testMultipleElements() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        Object[] array = queue.toArray();
        assert array.length == 3 : "Array length should be 3";
        assert array[0].equals(1) : "First element mismatch";
        assert array[1].equals(2) : "Second element mismatch";
        assert array[2].equals(3) : "Third element mismatch";
    }

    static void testNullElements() {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
        queue.offer(null);
        Object[] array = queue.toArray();
        assert array.length == 1 : "Array length should be 1";
        assert array[0] == null : "Element should be null";
    }

    static void testConcurrentModification() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("a");
        queue.offer("b");
        Thread t = new Thread(() -> {
            try {
                queue.offer("c");
            } catch (Exception e) {
                // ignore
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Object[] array = queue.toArray();
        assert array.length >= 2 : "Array should have at least 2 elements";
        boolean containsA = false, containsB = false, containsC = false;
        for (Object obj : array) {
            if ("a".equals(obj)) containsA = true;
            if ("b".equals(obj)) containsB = true;
            if ("c".equals(obj)) containsC = true;
        }
        assert containsA : "Queue should contain 'a'";
        assert containsB : "Queue should contain 'b'";
        assert containsC : "Queue should contain 'c'";
    }
}
