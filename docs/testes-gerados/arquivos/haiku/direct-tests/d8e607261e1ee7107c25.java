/*
 * @test
 * @summary Behavioral checks for CopyOnWriteArrayList iterator forEachRemaining
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testFullIteration();
        testRemainingAfterNext();
        testSnapshotIgnoresLaterModifications();
        testSecondCallIsNoOp();
        testNullElementsVisited();
        testEmptyList();
        testNullActionThrowsNpe();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFullIteration() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});
        List<String> seen = new ArrayList<>();
        list.iterator().forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "full iteration mismatch: " + seen);
    }

    static void testRemainingAfterNext() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});
        Iterator<String> it = list.iterator();
        check("a".equals(it.next()), "first next() should be a");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("b", "c")),
              "remaining elements mismatch: " + seen);
    }

    static void testSnapshotIgnoresLaterModifications() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b", "c"});
        Iterator<String> it = list.iterator();
        list.add("d");
        list.set(0, "x");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "snapshot iterator should not see changes: " + seen);
    }

    static void testSecondCallIsNoOp() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b"});
        Iterator<String> it = list.iterator();
        List<String> first = new ArrayList<>();
        it.forEachRemaining(first::add);
        check(first.equals(Arrays.asList("a", "b")),
              "first call mismatch: " + first);
        List<String> second = new ArrayList<>();
        it.forEachRemaining(second::add);
        check(second.isEmpty(), "second call should visit nothing: " + second);
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testNullElementsVisited() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{null, "x", null});
        List<String> seen = new ArrayList<>();
        list.iterator().forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList(null, "x", null)),
              "null elements mismatch: " + seen);
    }

    static void testEmptyList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        int[] count = new int[1];
        list.iterator().forEachRemaining(e -> count[0]++);
        check(count[0] == 0, "empty list should invoke action zero times");
    }

    static void testNullActionThrowsNpe() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", "b"});
        Iterator<String> it = list.iterator();
        boolean thrown = false;
        try {
            it.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action should throw NullPointerException");
        List<String> seen = new ArrayList<>();
        it.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("a", "b")),
              "iterator should be unchanged after NPE: " + seen);
    }
}
