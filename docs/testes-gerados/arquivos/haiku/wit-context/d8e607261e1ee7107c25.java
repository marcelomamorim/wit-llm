/*
 * @test
 * @summary WITUP-derived tests for CopyOnWriteArrayList COWIterator.forEachRemaining
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsAllElementsInOrder();
        testVisitsOnlyRemainingAfterNext();
        testSnapshotIgnoresLaterMutations();
        testExhaustedIteratorDoesNothing();
        testEmptyListDoesNothing();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNPE() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a", "b"));
        Iterator<String> it = list.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testVisitsAllElementsInOrder() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("x", null, "z"));
        Iterator<String> it = list.iterator();
        List<String> seen = new ArrayList<>();
        Consumer<String> action = seen::add;
        it.forEachRemaining(action);
        check(seen.size() == 3, "expected 3 visited elements but got " + seen.size());
        check("x".equals(seen.get(0)), "first element mismatch");
        check(seen.get(1) == null, "null element must be visited");
        check("z".equals(seen.get(2)), "last element mismatch");
        check(!it.hasNext(), "iterator must be exhausted after forEachRemaining");
    }

    static void testVisitsOnlyRemainingAfterNext() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3, 4));
        Iterator<Integer> it = list.iterator();
        check(it.next() == 1, "next() should return 1");
        check(it.next() == 2, "next() should return 2");
        List<Integer> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(3, 4)), "expected remaining [3, 4] but got " + seen);
        check(!it.hasNext(), "iterator must be exhausted");
    }

    static void testSnapshotIgnoresLaterMutations() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a", "b"));
        Iterator<String> it = list.iterator();
        list.add("c");
        list.remove("a");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("a", "b")),
              "snapshot iterator must see original elements only but got " + seen);
    }

    static void testExhaustedIteratorDoesNothing() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a"));
        Iterator<String> it = list.iterator();
        it.next();
        int[] calls = new int[1];
        it.forEachRemaining(s -> calls[0]++);
        check(calls[0] == 0, "no action calls expected on exhausted iterator");
    }

    static void testEmptyListDoesNothing() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        Iterator<String> it = list.iterator();
        int[] calls = new int[1];
        it.forEachRemaining(s -> calls[0]++);
        check(calls[0] == 0, "no action calls expected for empty list");
    }
}
