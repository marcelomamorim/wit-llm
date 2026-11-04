/*
 * @test
 * @summary Verifies ArrayList spliterator tryAdvance behavior: null action, empty list, in-order traversal and fail-fast on concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyListReturnsFalseWithoutAccept();
        testAdvancesInOrderOneElementAtATime();
        testConcurrentModificationDuringAccept();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNpe() {
        List<String> list = new ArrayList<>();
        list.add("a");
        Spliterator<String> sp = list.spliterator();
        try {
            sp.tryAdvance(null);
            check(false, "expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyListReturnsFalseWithoutAccept() {
        List<String> list = new ArrayList<>();
        Spliterator<String> sp = list.spliterator();
        int[] calls = new int[1];
        Consumer<String> counter = s -> calls[0]++;
        boolean advanced = sp.tryAdvance(counter);
        check(!advanced, "tryAdvance on empty list must return false");
        check(calls[0] == 0, "action must not be invoked on empty list");
    }

    static void testAdvancesInOrderOneElementAtATime() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Spliterator<String> sp = list.spliterator();
        List<String> seen = new ArrayList<>();
        Consumer<String> collector = seen::add;
        check(sp.tryAdvance(collector), "first tryAdvance must return true");
        check(sp.tryAdvance(collector), "second tryAdvance must return true");
        check(sp.tryAdvance(collector), "third tryAdvance must return true");
        check(!sp.tryAdvance(collector), "tryAdvance past end must return false");
        check(seen.size() == 3, "expected exactly 3 accepted elements");
        check(seen.get(0).equals("a"), "first element must be a");
        check(seen.get(1).equals("b"), "second element must be b");
        check(seen.get(2).equals("c"), "third element must be c");
    }

    static void testConcurrentModificationDuringAccept() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Spliterator<String> sp = list.spliterator();
        Consumer<String> modifier = s -> list.add("x");
        try {
            sp.tryAdvance(modifier);
            check(false, "expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
