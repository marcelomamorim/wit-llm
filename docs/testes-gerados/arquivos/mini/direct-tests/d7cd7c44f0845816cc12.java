/*
 * @test
 * @summary Test LinkedBlockingQueue removeIf method with various predicates
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveIfNullPredicate();
        testRemoveIfRemoveAll();
        testRemoveIfRemoveNone();
        testRemoveIfRemoveSome();
    }

    static void testRemoveIfNullPredicate() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.removeIf(null);
            throw new RuntimeException("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfRemoveAll() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        boolean changed = queue.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return true; // remove all
            }
        });
        if (!changed) {
            throw new RuntimeException("Expected removeIf to return true when elements removed");
        }
        if (!queue.isEmpty()) {
            throw new RuntimeException("Expected queue to be empty after removeIf removing all elements");
        }
    }

    static void testRemoveIfRemoveNone() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        boolean changed = queue.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return false; // remove none
            }
        });
        if (changed) {
            throw new RuntimeException("Expected removeIf to return false when no elements removed");
        }
        if (queue.size() != 3) {
            throw new RuntimeException("Expected queue size to remain 3 when no elements removed");
        }
        Iterator<String> it = queue.iterator();
        if (!it.next().equals("a") || !it.next().equals("b") || !it.next().equals("c")) {
            throw new RuntimeException("Expected queue elements to remain unchanged");
        }
    }

    static void testRemoveIfRemoveSome() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        queue.add("d");
        boolean changed = queue.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return "b".equals(s) || "d".equals(s);
            }
        });
        if (!changed) {
            throw new RuntimeException("Expected removeIf to return true when some elements removed");
        }
        if (queue.size() != 2) {
            throw new RuntimeException("Expected queue size to be 2 after removing some elements");
        }
        Iterator<String> it = queue.iterator();
        if (!it.next().equals("a") || !it.next().equals("c")) {
            throw new RuntimeException("Expected queue to contain only elements not removed");
        }
    }
}
