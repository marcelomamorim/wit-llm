/*
 * @test
 * @summary WITUP-derived checks for ArrayList spliterator forEachRemaining null handling and observable behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        Spliterator<String> sp = list.spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testNullActionOnEmptyListThrowsNPE() {
        ArrayList<String> list = new ArrayList<>();
        Spliterator<String> sp = list.spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action on empty list");
    }

    static void testVisitsAllElementsInOrder() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add(null);
        list.add("z");
        List<String> seen = new ArrayList<>();
        list.spliterator().forEachRemaining(seen::add);
        check(seen.size() == 3, "expected 3 visited elements but got " + seen.size());
        check("x".equals(seen.get(0)), "first element mismatch");
        check(seen.get(1) == null, "null element should be visited");
        check("z".equals(seen.get(2)), "last element mismatch");
    }

    static void testEmptyListNoCalls() {
        ArrayList<String> list = new ArrayList<>();
        int[] calls = new int[1];
        list.spliterator().forEachRemaining(s -> calls[0]++);
        check(calls[0] == 0, "action should not be called for empty list");
    }

    static void testConcurrentModificationDuringTraversal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        boolean thrown = false;
        try {
            list.spliterator().forEachRemaining(s -> list.add("c"));
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException when list is structurally modified");
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionOnEmptyListThrowsNPE();
        testVisitsAllElementsInOrder();
        testEmptyListNoCalls();
        testConcurrentModificationDuringTraversal();
    }
}
