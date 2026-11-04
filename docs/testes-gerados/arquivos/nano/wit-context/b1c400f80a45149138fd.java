/*
 * @test
 * @summary Tests for LinkedBlockingQueue.iterator()
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testIteratorReturnsElements();
        testIteratorThrowsNoSuchElementException();
    }

    private static void testIteratorReturnsElements() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("element1");
        queue.offer("element2");
        Iterator<String> it = queue.iterator();
        boolean foundElement1 = false;
        boolean foundElement2 = false;
        while (it.hasNext()) {
            String element = it.next();
            if ("element1".equals(element)) {
                foundElement1 = true;
            } else if ("element2".equals(element)) {
                foundElement2 = true;
            }
        }
        assert foundElement1 : "element1 not found in iterator";
        assert foundElement2 : "element2 not found in iterator";
    }

    private static void testIteratorThrowsNoSuchElementException() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("element");
        Iterator<String> it = queue.iterator();
        try {
            while (true) {
                it.next();
            }
        } catch (NoSuchElementException e) {
            // Expected exception
            return;
        }
        assert false : "Expected NoSuchElementException was not thrown";
    }
}
