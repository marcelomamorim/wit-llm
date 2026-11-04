/*
 * @test
 * @summary Verify PriorityBlockingQueue iterator next() throws NoSuchElementException when no elements remain
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueIteratorNextThrows();
        testExhaustedIteratorNextThrowsRepeatedly();
        System.out.println("PASSED");
    }

    static void testEmptyQueueIteratorNextThrows() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Iterator<Integer> it = queue.iterator();
        try {
            it.next();
            throw new AssertionError("expected NoSuchElementException on empty queue iterator");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testExhaustedIteratorNextThrowsRepeatedly() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(1);
        queue.add(2);
        Iterator<Integer> it = queue.iterator();

        Set<Integer> seen = new HashSet<>();
        seen.add(it.next());
        seen.add(it.next());
        if (seen.size() != 2 || !seen.contains(1) || !seen.contains(2)) {
            throw new AssertionError("unexpected elements returned by iterator: " + seen);
        }

        for (int i = 0; i < 2; i++) {
            try {
                it.next();
                throw new AssertionError("expected NoSuchElementException after exhaustion, attempt " + i);
            } catch (NoSuchElementException expected) {
            }
        }
    }
}
