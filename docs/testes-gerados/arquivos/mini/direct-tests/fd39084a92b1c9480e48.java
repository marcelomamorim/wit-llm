/*
 * @test
 * @summary Test ArrayBlockingQueue.remove(Object) method behavior
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveNullReturnsFalse();
        testRemoveFromEmptyReturnsFalse();
        testRemoveExistingElementReturnsTrue();
        testRemoveNonExistingElementReturnsFalse();
        testRemoveMultipleElements();
    }

    static void testRemoveNullReturnsFalse() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        boolean removed = q.remove(null);
        if (removed) throw new RuntimeException("remove(null) should return false");
    }

    static void testRemoveFromEmptyReturnsFalse() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        boolean removed = q.remove("foo");
        if (removed) throw new RuntimeException("remove on empty queue should return false");
    }

    static void testRemoveExistingElementReturnsTrue() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        q.add("foo");
        q.add("bar");
        boolean removed = q.remove("foo");
        if (!removed) throw new RuntimeException("remove existing element should return true");
        if (q.size() != 1) throw new RuntimeException("queue size should be 1 after removal");
        if (q.contains("foo")) throw new RuntimeException("queue should not contain removed element");
    }

    static void testRemoveNonExistingElementReturnsFalse() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        q.add("foo");
        boolean removed = q.remove("bar");
        if (removed) throw new RuntimeException("remove non-existing element should return false");
        if (q.size() != 1) throw new RuntimeException("queue size should remain 1");
    }

    static void testRemoveMultipleElements() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        q.add("a");
        q.add("b");
        q.add("c");
        boolean removedB = q.remove("b");
        boolean removedC = q.remove("c");
        boolean removedD = q.remove("d");
        if (!removedB) throw new RuntimeException("remove existing element 'b' should return true");
        if (!removedC) throw new RuntimeException("remove existing element 'c' should return true");
        if (removedD) throw new RuntimeException("remove non-existing element 'd' should return false");
        if (q.size() != 1) throw new RuntimeException("queue size should be 1 after removals");
        if (!q.contains("a")) throw new RuntimeException("queue should still contain 'a'");
    }
}
