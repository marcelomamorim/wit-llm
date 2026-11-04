/*
 * @test
 * @summary Verifies LinkedTransferQueue.removeIf null-filter handling and observable removal behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Predicate;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testNullFilterThrows();
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testEmptyQueueReturnsFalse();
        testRemoveAllMatching();
        testSecondCallReturnsFalse();
        testConstructedFromCollection();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFilterThrows() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        boolean thrown = false;
        try {
            q.removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) should throw NullPointerException");
        check(q.size() == 1, "queue should be unchanged after null filter");
    }

    static void testRemovesMatchingElements() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        for (int i = 1; i <= 6; i++) {
            q.add(i);
        }
        Predicate<Integer> even = x -> x % 2 == 0;
        check(q.removeIf(even), "removeIf should return true when elements match");
        check(Arrays.equals(q.toArray(), new Object[]{1, 3, 5}),
              "remaining elements should be [1, 3, 5] in FIFO order");
    }

    static void testNoMatchReturnsFalse() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(3);
        Predicate<Integer> even = x -> x % 2 == 0;
        check(!q.removeIf(even), "removeIf should return false when no element matches");
        check(q.size() == 2, "size should remain 2");
    }

    static void testEmptyQueueReturnsFalse() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        check(!q.removeIf(x -> true), "removeIf on empty queue should return false");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testRemoveAllMatching() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        check(q.removeIf(x -> true), "removeIf should return true when all elements match");
        check(q.isEmpty(), "queue should be empty after removing all elements");
    }

    static void testSecondCallReturnsFalse() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(2);
        q.add(3);
        Predicate<Integer> even = x -> x % 2 == 0;
        check(q.removeIf(even), "first removeIf should return true");
        check(!q.removeIf(even), "second removeIf should return false");
        check(Arrays.equals(q.toArray(), new Object[]{3}), "only 3 should remain");
    }

    static void testConstructedFromCollection() {
        LinkedTransferQueue<Integer> q =
            new LinkedTransferQueue<>(Arrays.asList(1, 2, 3, 4));
        check(q.removeIf(x -> x % 2 == 1), "removeIf should remove odd elements");
        check(Arrays.equals(q.toArray(), new Object[]{2, 4}),
              "remaining elements should be [2, 4]");
    }
}
