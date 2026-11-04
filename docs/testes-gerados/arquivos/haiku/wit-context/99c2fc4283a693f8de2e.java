/*
 * @test
 * @summary Witup-generated checks for LinkedTransferQueue spliterator tryAdvance
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyQueueReturnsFalseWithoutAction();
        testConsumesElementsInFifoOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        Spliterator<String> spliterator = queue.spliterator();
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyQueueReturnsFalseWithoutAction() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        Spliterator<String> spliterator = queue.spliterator();
        final int[] calls = {0};
        boolean advanced = spliterator.tryAdvance(x -> calls[0]++);
        check(!advanced, "tryAdvance on empty queue should return false");
        check(calls[0] == 0, "action must not be invoked on empty queue");
    }

    static void testConsumesElementsInFifoOrder() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Spliterator<String> spliterator = queue.spliterator();
        List<String> seen = new ArrayList<>();
        check(spliterator.tryAdvance(seen::add), "first tryAdvance should return true");
        check(spliterator.tryAdvance(seen::add), "second tryAdvance should return true");
        check(spliterator.tryAdvance(seen::add), "third tryAdvance should return true");
        check(!spliterator.tryAdvance(seen::add), "tryAdvance after draining should return false");
        check(seen.size() == 3, "expected exactly 3 consumed elements but got " + seen.size());
        check(seen.get(0).equals("a"), "first consumed element should be a");
        check(seen.get(1).equals("b"), "second consumed element should be b");
        check(seen.get(2).equals("c"), "third consumed element should be c");
    }
}
