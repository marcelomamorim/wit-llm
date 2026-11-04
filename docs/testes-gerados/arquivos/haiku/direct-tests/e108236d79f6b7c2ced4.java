/*
 * @test
 * @summary Verifies LinkedTransferQueue iterator forEachRemaining traversal order, partial iteration and null action handling
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testFullTraversal();
        testEmptyQueue();
        testPartialTraversal();
        testNullActionThrowsNpe();
        testConstructorFromCollection();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFullTraversal() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        List<Integer> seen = new ArrayList<>();
        Iterator<Integer> it = q.iterator();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(1, 2, 3)),
              'forEachRemaining should visit elements in FIFO order: ' + seen);
        check(!it.hasNext(), 'iterator should be exhausted after forEachRemaining');
    }

    static void testEmptyQueue() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        List<Integer> seen = new ArrayList<>();
        Iterator<Integer> it = q.iterator();
        it.forEachRemaining(seen::add);
        check(seen.isEmpty(), 'forEachRemaining on empty queue must not invoke action');
        check(!it.hasNext(), 'iterator of empty queue should have no next');
    }

    static void testPartialTraversal() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        Iterator<Integer> it = q.iterator();
        check(it.next() == 1, 'first next() should return 1');
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(2, 3)),
              'forEachRemaining after next() should visit only remaining elements: ' + seen);
        check(!it.hasNext(), 'iterator should be exhausted after partial forEachRemaining');
    }

    static void testNullActionThrowsNpe() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        Iterator<Integer> it = q.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, 'forEachRemaining(null) must throw NullPointerException');
    }

    static void testConstructorFromCollection() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>(Arrays.asList(4, 5));
        List<Integer> seen = new ArrayList<>();
        q.iterator().forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(4, 5)),
              'queue built from collection should traverse in order: ' + seen);
    }
}
