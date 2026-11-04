/*
 * @test
 * @summary Tests ArrayList spliterator tryAdvance behavior: order, end of list, null elements, null action and concurrent modification
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testEmptyListReturnsFalseAndDoesNotAccept();
        testIteratesElementsInOrderThenReturnsFalse();
        testNullElementIsPassedToAction();
        testNullActionThrowsNullPointerException();
        testConcurrentModificationIsDetected();
        testPartialConsumptionAdvancesOneElementPerCall();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyListReturnsFalseAndDoesNotAccept() {
        List<String> list = new ArrayList<>();
        Spliterator<String> sp = list.spliterator();
        int[] calls = {0};
        boolean result = sp.tryAdvance(x -> calls[0]++);
        check(!result, "tryAdvance on empty list must return false");
        check(calls[0] == 0, "action must not be invoked on empty list");
    }

    static void testIteratesElementsInOrderThenReturnsFalse() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Spliterator<String> sp = list.spliterator();
        List<String> seen = new ArrayList<>();
        int steps = 0;
        while (sp.tryAdvance(seen::add)) {
            steps++;
        }
        check(steps == 3, "expected 3 successful advances but got " + steps);
        check(seen.equals(list), "elements must be visited in order: " + seen);
        check(!sp.tryAdvance(seen::add), "tryAdvance after end must return false");
        check(seen.size() == 3, "no extra element must be accepted after end");
    }

    static void testNullElementIsPassedToAction() {
        List<String> list = new ArrayList<>();
        list.add(null);
        Spliterator<String> sp = list.spliterator();
        Object[] box = {"sentinel"};
        boolean result = sp.tryAdvance(x -> box[0] = x);
        check(result, "tryAdvance must return true for a null element");
        check(box[0] == null, "action must receive the null element");
    }

    static void testNullActionThrowsNullPointerException() {
        List<String> list = new ArrayList<>(Arrays.asList("x"));
        Spliterator<String> sp = list.spliterator();
        try {
            sp.tryAdvance(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testConcurrentModificationIsDetected() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Spliterator<Integer> sp = list.spliterator();
        try {
            sp.tryAdvance(x -> list.add(99));
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testPartialConsumptionAdvancesOneElementPerCall() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Spliterator<Integer> sp = list.spliterator();
        Integer[] box = {null};
        check(sp.tryAdvance(x -> box[0] = x), "first advance must succeed");
        check(box[0] == 1, "first element must be 1 but was " + box[0]);
        check(sp.tryAdvance(x -> box[0] = x), "second advance must succeed");
        check(box[0] == 2, "second element must be 2 but was " + box[0]);
    }
}
