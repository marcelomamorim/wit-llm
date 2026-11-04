/*
 * @test
 * @summary Verifies PriorityBlockingQueue iterator next() returns each snapshot element once and throws NoSuchElementException when exhausted
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptyQueueNextThrows() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        Iterator<Integer> it = q.iterator();
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException on empty queue");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testIterationReturnsAllElementsOnce() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>(4);
        int[] values = {5, 1, 4, 2, 3};
        for (int v : values) {
            q.add(v);
        }
        Iterator<Integer> it = q.iterator();
        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }
        check(seen.size() == values.length,
              "expected " + values.length + " elements but got " + seen.size());
        Collections.sort(seen);
        for (int i = 0; i < values.length; i++) {
            check(seen.get(i) == i + 1, "unexpected element at " + i + ": " + seen.get(i));
        }
    }

    static void testNextAfterExhaustionThrows() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(7);
        Iterator<Integer> it = q.iterator();
        check(it.next() == 7, "first next() should return 7");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException after exhaustion");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testIteratorIsSnapshotAfterPoll() {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        Iterator<Integer> it = q.iterator();
        while (!q.isEmpty()) {
            q.poll();
        }
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        check(count == 3, "snapshot iterator should still yield 3 elements, got " + count);
    }

    public static void main(String[] args) {
        testEmptyQueueNextThrows();
        testIterationReturnsAllElementsOnce();
        testNextAfterExhaustionThrows();
        testIteratorIsSnapshotAfterPoll();
    }
}
