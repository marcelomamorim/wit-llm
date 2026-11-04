/*
 * @test
 * @summary Test LinkedBlockingQueue.remove(Object) method behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveNullReturnsFalse();
        testRemoveNonExistentElementReturnsFalse();
        testRemoveExistingElementReturnsTrueAndRemoves();
        testRemoveMultipleElements();
    }

    private static void testRemoveNullReturnsFalse() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        boolean removed = queue.remove(null);
        if (removed) {
            throw new AssertionError("remove(null) should return false");
        }
    }

    private static void testRemoveNonExistentElementReturnsFalse() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");
        boolean removed = queue.remove("three");
        if (removed) {
            throw new AssertionError("remove(non-existent) should return false");
        }
        // queue should remain unchanged
        if (queue.size() != 2 || !queue.contains("one") || !queue.contains("two")) {
            throw new AssertionError("Queue changed after removing non-existent element");
        }
    }

    private static void testRemoveExistingElementReturnsTrueAndRemoves() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");
        boolean removed = queue.remove("one");
        if (!removed) {
            throw new AssertionError("remove(existing) should return true");
        }
        if (queue.size() != 1 || queue.contains("one")) {
            throw new AssertionError("Element was not removed properly");
        }
    }

    private static void testRemoveMultipleElements() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");
        queue.add("three");
        boolean removedTwo = queue.remove("two");
        boolean removedThree = queue.remove("three");
        if (!removedTwo || !removedThree) {
            throw new AssertionError("remove should return true for existing elements");
        }
        if (queue.size() != 1 || !queue.contains("one") || queue.contains("two") || queue.contains("three")) {
            throw new AssertionError("Queue state incorrect after multiple removes");
        }
    }
}
