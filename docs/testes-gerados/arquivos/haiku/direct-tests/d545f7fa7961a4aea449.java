/*
 * @test
 * @summary Tests for CopyOnWriteArrayList.forEach null handling, ordering, empty list, snapshot semantics and null elements
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsElementsInOrder();
        testEmptyListNoAction();
        testSnapshotDuringIteration();
        testNullElementsVisited();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a"});
        boolean thrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testVisitsElementsInOrder() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> visited = new ArrayList<>();
        list.forEach(visited::add);
        check(visited.equals(Arrays.asList("a", "b", "c")),
              "forEach must visit elements in order, got " + visited);
    }

    static void testEmptyListNoAction() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        int[] count = {0};
        list.forEach(e -> count[0]++);
        check(count[0] == 0, "forEach on empty list must not invoke action");
    }

    static void testSnapshotDuringIteration() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(Arrays.asList("a", "b"));
        int[] count = {0};
        list.forEach(e -> {
            count[0]++;
            list.add("x");
        });
        check(count[0] == 2, "forEach must visit only the snapshot elements, visited " + count[0]);
        check(list.size() == 4, "list must reflect additions made during iteration, size " + list.size());
    }

    static void testNullElementsVisited() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(new String[]{"a", null, "c"});
        List<String> visited = new ArrayList<>();
        list.forEach(visited::add);
        check(visited.size() == 3, "forEach must visit null elements, size " + visited.size());
        check(visited.get(1) == null, "null element must be passed to action");
        check("c".equals(visited.get(2)), "last element must be c");
    }
}
