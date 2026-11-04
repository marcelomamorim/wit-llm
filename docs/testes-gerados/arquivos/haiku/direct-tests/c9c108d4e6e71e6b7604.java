/*
 * @test
 * @summary Behavioral checks for ArrayBlockingQueue.clear() using only public API
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testClearEmptiesNonEmptyQueue() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5);
        q.add(1);
        q.add(2);
        q.add(3);
        q.clear();
        check(q.isEmpty(), "queue should be empty after clear");
        check(q.size() == 0, "size should be 0 after clear");
        check(q.poll() == null, "poll should return null after clear");
        check(q.peek() == null, "peek should return null after clear");
        check(q.remainingCapacity() == 5, "remaining capacity should equal capacity after clear");
    }

    static void testClearOnEmptyQueueIsNoOp() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.clear();
        check(q.isEmpty(), "empty queue should remain empty");
        check(q.size() == 0, "size should remain 0");
        check(q.offer(7), "offer should succeed after clear on empty queue");
        check(q.poll() == 7, "element should be retrievable after clear on empty queue");
    }

    static void testClearOnFullQueueAllowsRefill() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        check(!q.offer(4), "offer on full queue should fail before clear");
        q.clear();
        check(q.remainingCapacity() == 3, "full capacity should be available after clear");
        check(q.offer(10), "offer should succeed after clear");
        check(q.offer(20), "offer should succeed after clear");
        check(q.offer(30), "offer should succeed after clear");
        check(!q.offer(40), "queue should be full again after refilling to capacity");
        check(q.poll() == 10, "FIFO order should be preserved after clear");
        check(q.poll() == 20, "FIFO order should be preserved after clear");
        check(q.poll() == 30, "FIFO order should be preserved after clear");
        check(q.poll() == null, "queue should be empty at the end");
    }

    static void testClearAfterWraparound() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        check(q.poll() == 1, "first poll should return 1");
        check(q.poll() == 2, "second poll should return 2");
        q.add(5);
        q.add(6);
        q.clear();
        check(q.isEmpty(), "queue should be empty after clear with wrapped indices");
        check(q.remainingCapacity() == 4, "capacity should be restored after clear");
        q.add(7);
        q.add(8);
        check(q.poll() == 7, "FIFO order should hold after clear with wrapped indices");
        check(q.poll() == 8, "FIFO order should hold after clear with wrapped indices");
        check(q.poll() == null, "queue should be empty");
    }

    static void testClearTwiceIsSafe() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(1);
        q.clear();
        q.clear();
        check(q.isEmpty(), "queue should be empty after repeated clear");
        check(q.remainingCapacity() == 2, "capacity should be intact after repeated clear");
    }

    static void testIteratorAfterClearHasNoElements() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.clear();
        check(!q.iterator().hasNext(), "iterator created after clear should be empty");
        check(q.toArray().length == 0, "toArray should be empty after clear");
    }

    static void testClearWithInitialCollectionConstructor() {
        List<Integer> init = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5, true, init);
        check(q.size() == 3, "initial size should be 3");
        q.clear();
        check(q.isEmpty(), "queue should be empty after clear");
        check(q.remainingCapacity() == 5, "capacity should be 5 after clear");
    }

    public static void main(String[] args) {
        testClearEmptiesNonEmptyQueue();
        testClearOnEmptyQueueIsNoOp();
        testClearOnFullQueueAllowsRefill();
        testClearAfterWraparound();
        testClearTwiceIsSafe();
        testIteratorAfterClearHasNoElements();
        testClearWithInitialCollectionConstructor();
    }
}
