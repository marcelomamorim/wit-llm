/*
 * @test    @summary Test for ArrayBlockingQueue.iterator() method.
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Iterator;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testIteratorReturnsExpected();
        testIteratorOnEmptyQueue();
    }

    private static void testIteratorReturnsExpected() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.add("A");
        queue.add("B");
        queue.add("C");
        Iterator<String> it = queue.iterator();
        boolean hasNext = it.hasNext();
        String first = it.next();
        String second = it.next();
        String third = it.next();
        boolean noMore = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            noMore = true;
        }
        assert hasNext : "Iterator should have next element";
        assert first.equals("A") : "First element should be A";
        assert second.equals("B") : "Second element should be B";
        assert third.equals("C") : "Third element should be C";
        assert noMore : "Iterator should throw NoSuchElementException at end";
    }

    private static void testIteratorOnEmptyQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        Iterator<String> it = queue.iterator();
        boolean hasNext = it.hasNext();
        boolean exceptionThrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        assert !hasNext : "Iterator on empty queue should not have next";
        assert exceptionThrown : "Calling next() on empty iterator should throw NoSuchElementException";
    }
}
