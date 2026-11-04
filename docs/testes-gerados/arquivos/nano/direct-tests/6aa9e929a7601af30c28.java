/*
 * @test    @summary Test the iterator() method of ArrayBlockingQueue for correct iteration behavior.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueue();
        testSingleElement();
        testMultipleElements();
    }

    static void testEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        Iterator<String> it = queue.iterator();
        boolean hasNext = it.hasNext();
        if (hasNext) {
            throw new AssertionError("Expected no elements in iterator for empty queue");
        }
    }

    static void testSingleElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("element");
        Iterator<String> it = queue.iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        String val = it.next();
        if (!"element".equals(val)) {
            throw new AssertionError("Expected 'element', got: " + val);
        }
        if (it.hasNext()) {
            throw new AssertionError("Iterator should not have next after consuming element");
        }
    }

    static void testMultipleElements() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("first");
        queue.add("second");
        queue.add("third");
        Iterator<String> it = queue.iterator();
        String[] expected = {"first", "second", "third"};
        int index = 0;
        while (it.hasNext()) {
            String val = it.next();
            if (!expected[index].equals(val)) {
                throw new AssertionError("Expected '" + expected[index] + "', got: " + val);
            }
            index++;
        }
        if (index != expected.length) {
            throw new AssertionError("Iterator did not traverse all elements");
        }
    }
}
