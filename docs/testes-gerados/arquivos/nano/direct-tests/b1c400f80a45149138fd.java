/*
 * @test
 * @summary Test the iterator() method of LinkedBlockingQueue for correct iteration behavior.
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueIterator();
        testSingleElementQueueIterator();
        testMultipleElementsQueueIterator();
        testIteratorRemoveThrows();
    }

    static void testEmptyQueueIterator() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Iterator<String> it = queue.iterator();
        assert !it.hasNext() : "Empty queue iterator should have no next";
        try {
            it.next();
            assert false : "Calling next() on empty iterator should throw";
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    static void testSingleElementQueueIterator() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("element");
        Iterator<String> it = queue.iterator();
        assert it.hasNext() : "Iterator should have next when queue has element";
        String val = it.next();
        assert "element".equals(val) : "Iterator should return the correct element";
        assert !it.hasNext() : "Iterator should have no next after consuming element";
        try {
            it.next();
            assert false : "Calling next() after end should throw";
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    static void testMultipleElementsQueueIterator() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        Iterator<String> it = queue.iterator();
        String[] expected = {"a", "b", "c"};
        int index = 0;
        while (it.hasNext()) {
            String val = it.next();
            assert expected[index].equals(val) : "Iterator returned unexpected element";
            index++;
        }
        assert index == expected.length : "Iterator should have iterated over all elements";
    }

    static void testIteratorRemoveThrows() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("x");
        Iterator<String> it = queue.iterator();
        try {
            it.remove();
            assert false : "remove() should throw UnsupportedOperationException";
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
