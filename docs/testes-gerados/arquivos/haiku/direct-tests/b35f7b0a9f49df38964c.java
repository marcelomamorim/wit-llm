/*
 * @test
 * @summary Checks ConcurrentLinkedDeque iterator forEachRemaining behavior via public API
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemainingElementsAfterNext();
        testSecondCallIsNoOp();
        testEmptyDeque();
        testNullActionThrowsNPE();
    }

    static void testRemainingElementsAfterNext() {
        ConcurrentLinkedDeque<String> d =
            new ConcurrentLinkedDeque<>(Arrays.asList("a", "b", "c"));
        Iterator<String> it = d.iterator();
        check("a".equals(it.next()), "first next() should be a");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("b", "c")), "remaining elements: " + seen);
    }

    static void testSecondCallIsNoOp() {
        ConcurrentLinkedDeque<String> d =
            new ConcurrentLinkedDeque<>(Arrays.asList("x", "y"));
        Iterator<String> it = d.iterator();
        List<String> first = new ArrayList<>();
        it.forEachRemaining(first::add);
        check(first.equals(Arrays.asList("x", "y")), "first traversal: " + first);
        List<String> second = new ArrayList<>();
        it.forEachRemaining(second::add);
        check(second.isEmpty(), "second traversal should be empty: " + second);
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testEmptyDeque() {
        Iterator<String> it = new ConcurrentLinkedDeque<String>().iterator();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.isEmpty(), "empty deque should produce no elements: " + seen);
    }

    static void testNullActionThrowsNPE() {
        ConcurrentLinkedDeque<String> d =
            new ConcurrentLinkedDeque<>(Arrays.asList("a"));
        boolean thrown = false;
        try {
            d.iterator().forEachRemaining(null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
