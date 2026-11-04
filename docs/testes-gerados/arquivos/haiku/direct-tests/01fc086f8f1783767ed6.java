/*
 * @test
 * @summary Verifies ArrayList spliterator forEachRemaining behavior through public API
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemainingAfterTryAdvance();
        testEmptyListForEachRemaining();
        testNullActionThrowsNPE();
        testConcurrentModificationDetected();
    }

    static void testRemainingAfterTryAdvance() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Spliterator<String> sp = list.spliterator();
        List<String> seen = new ArrayList<>();
        sp.tryAdvance(seen::add);
        sp.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "expected [a, b, c] but got " + seen);
    }

    static void testEmptyListForEachRemaining() {
        List<String> list = new ArrayList<>();
        final int[] count = {0};
        list.spliterator().forEachRemaining(x -> count[0]++);
        check(count[0] == 0, "expected no elements, got " + count[0]);
    }

    static void testNullActionThrowsNPE() {
        List<String> list = new ArrayList<>();
        list.add("a");
        boolean thrown = false;
        try {
            list.spliterator().forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testConcurrentModificationDetected() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Spliterator<String> sp = list.spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(x -> list.add("z"));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException when list is modified during traversal");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
