/*
 * @test
 * @summary Witup-generated tests for ArrayBlockingQueue.removeAll
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullCollectionThrowsNpe();
        testRemoveAllRemovesMatchingElements();
        testRemoveAllWithNoMatch();
        testRemoveAllOnEmptyQueue();
        testRemoveAllWithEmptyCollection();
    }

    static void testNullCollectionThrowsNpe() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.add(1);
        q.add(2);
        boolean thrown = false;
        try {
            q.removeAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null collection");
        }
        if (q.size() != 2 || !q.contains(1) || !q.contains(2)) {
            throw new RuntimeException("queue modified after failed removeAll(null)");
        }
    }

    static void testRemoveAllRemovesMatchingElements() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(8);
        for (int i = 1; i <= 4; i++) {
            q.add(i);
        }
        boolean changed = q.removeAll(Arrays.asList(2, 4));
        if (!changed) {
            throw new RuntimeException("removeAll should return true when elements are removed");
        }
        if (q.size() != 2) {
            throw new RuntimeException("expected size 2 but was " + q.size());
        }
        if (q.contains(2) || q.contains(4)) {
            throw new RuntimeException("removed elements still present");
        }
        if (!q.contains(1) || !q.contains(3)) {
            throw new RuntimeException("retained elements missing");
        }
    }

    static void testRemoveAllWithNoMatch() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.add(1);
        q.add(3);
        boolean changed = q.removeAll(Arrays.asList(9));
        if (changed) {
            throw new RuntimeException("removeAll should return false when nothing matches");
        }
        if (q.size() != 2 || !q.contains(1) || !q.contains(3)) {
            throw new RuntimeException("queue changed when nothing matched");
        }
    }

    static void testRemoveAllOnEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        boolean changed = q.removeAll(Arrays.asList(1));
        if (changed) {
            throw new RuntimeException("removeAll on empty queue should return false");
        }
        if (!q.isEmpty()) {
            throw new RuntimeException("empty queue became non-empty");
        }
    }

    static void testRemoveAllWithEmptyCollection() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.add(5);
        boolean changed = q.removeAll(Collections.emptyList());
        if (changed) {
            throw new RuntimeException("removeAll with empty collection should return false");
        }
        if (q.size() != 1 || !q.contains(5)) {
            throw new RuntimeException("queue changed with empty collection");
        }
    }
}
