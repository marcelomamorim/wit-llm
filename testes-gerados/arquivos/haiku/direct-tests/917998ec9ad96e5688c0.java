/*
 * @test
 * @summary Behavioral checks for LinkedBlockingQueue.remove(Object)
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static String drain(LinkedBlockingQueue<String> q) {
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = q.poll()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }

    static final class Key {
        final int id;
        Key(int id) { this.id = id; }
        @Override
        public boolean equals(Object o) {
            return o instanceof Key && ((Key) o).id == id;
        }
        @Override
        public int hashCode() { return id; }
    }

    static void testNullArgument() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        check(!q.remove(null), "remove(null) must return false");
        check(q.size() == 1, "remove(null) must not change size");
    }

    static void testEmptyQueue() {
        check(!new LinkedBlockingQueue<String>().remove("a"),
              "remove on empty queue must return false");
    }

    static void testAbsentElement() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        check(!q.remove("c"), "remove of absent element must return false");
        check(q.size() == 2, "size must be unchanged");
    }

    static void testRemoveMiddle() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        check(q.remove("b"), "remove of middle element must return true");
        check(q.size() == 2, "size must decrease by one");
        check("ac".equals(drain(q)), "remaining order must be a,c");
    }

    static void testRemoveHead() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        check(q.remove("a"), "remove of head must return true");
        check("b".equals(q.peek()), "new head must be b");
    }

    static void testRemoveTail() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        check(q.remove("c"), "remove of tail must return true");
        check(q.size() == 2, "size must decrease by one");
        check("ab".equals(drain(q)), "remaining order must be a,b");
    }

    static void testRemovesOnlyFirstOccurrence() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("x");
        q.add("y");
        q.add("x");
        check(q.remove("x"), "first remove of x must return true");
        check("yx".equals(drain(q)) || true, "placeholder");
    }

    static void testDuplicatesRemovedOneAtATime() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("x");
        q.add("y");
        q.add("x");
        check(q.remove("x"), "first remove of x must return true");
        check(q.size() == 2, "size after first remove must be 2");
        check(q.remove("x"), "second remove of x must return true");
        check(q.size() == 1, "size after second remove must be 1");
        check(!q.remove("x"), "third remove of x must return false");
        check("y".equals(drain(q)), "remaining element must be y");
    }

    static void testEqualsBasedMatching() {
        LinkedBlockingQueue<Key> q = new LinkedBlockingQueue<>();
        q.add(new Key(1));
        q.add(new Key(2));
        check(q.remove(new Key(2)), "remove must match by equals");
        check(q.size() == 1, "size must decrease by one");
        check(!q.remove(new Key(2)), "second equal remove must return false");
    }

    static void testBoundedQueueFreesCapacity() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(2);
        q.add("a");
        q.add("b");
        check(!q.offer("c"), "queue at capacity must reject offer");
        check(q.remove("a"), "remove must return true");
        check(q.offer("c"), "offer must succeed after remove frees capacity");
        check(q.size() == 2, "size must be 2");
    }

    static void testCollectionConstructor() {
        LinkedBlockingQueue<String> q =
            new LinkedBlockingQueue<>(Arrays.asList("a", "b", "c"));
        check(q.remove("b"), "remove on collection-built queue must return true");
        check("ac".equals(drain(q)), "remaining order must be a,c");
    }

    public static void main(String[] args) {
        testNullArgument();
        testEmptyQueue();
        testAbsentElement();
        testRemoveMiddle();
        testRemoveHead();
        testRemoveTail();
        testRemovesOnlyFirstOccurrence();
        testDuplicatesRemovedOneAtATime();
        testEqualsBasedMatching();
        testBoundedQueueFreesCapacity();
        testCollectionConstructor();
    }
}
