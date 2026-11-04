/*
 * @test
 * @summary Tests CopyOnWriteArrayList iterator forEachRemaining behavior: remaining elements, exhausted iterator, snapshot semantics, null elements and null action
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testRemainingElementsAfterNext();
        testExhaustedIteratorPerformsNoAction();
        testSnapshotIgnoresLaterMutations();
        testNullElementsAreVisited();
        testNullActionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemainingElementsAfterNext() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b", "c", "d"});
        Iterator<String> it = list.iterator();
        check("a".equals(it.next()), "first next() should return a");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("b", "c", "d")),
              "forEachRemaining should visit remaining elements, got " + seen);
    }

    static void testExhaustedIteratorPerformsNoAction() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"x", "y"});
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            it.next();
        }
        int[] counter = {0};
        it.forEachRemaining(e -> counter[0]++);
        check(counter[0] == 0, "exhausted iterator must not invoke action");
    }

    static void testSnapshotIgnoresLaterMutations() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"x", "y"});
        Iterator<String> it = list.iterator();
        list.add("z");
        list.set(0, "w");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("x", "y")),
              "iterator must reflect snapshot taken at creation, got " + seen);
    }

    static void testNullElementsAreVisited() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{null, "b", null});
        Iterator<String> it = list.iterator();
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(null, "b", null)),
              "null elements must be visited, got " + seen);
    }

    static void testNullActionThrowsNPE() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a"});
        Iterator<String> it = list.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }
}
