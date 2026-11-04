/*
 * @test
 * @summary Witup-generated tests for CopyOnWriteArrayList.forEach
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testIteratesInOrder();
        testNullElementsAreVisited();
        testSnapshotIgnoresConcurrentAdditions();
        testEmptyListDoesNotInvokeAction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(Arrays.asList("a", "b"));
        boolean thrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) should throw NullPointerException");
    }

    static void testIteratesInOrder() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> seen = new ArrayList<>();
        list.forEach(x -> seen.add(x));
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "forEach should visit elements in order, got " + seen);
    }

    static void testNullElementsAreVisited() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(new String[] {"x", null});
        List<String> seen = new ArrayList<>();
        list.forEach(x -> seen.add(x));
        check(seen.size() == 2, "forEach should visit null elements, size=" + seen.size());
        check("x".equals(seen.get(0)), "first element should be x");
        check(seen.get(1) == null, "second element should be null");
    }

    static void testSnapshotIgnoresConcurrentAdditions() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(Arrays.asList("a", "b"));
        List<String> seen = new ArrayList<>();
        list.forEach(x -> {
            seen.add(x);
            if (x.equals("a")) {
                list.add("c");
            }
        });
        check(seen.equals(Arrays.asList("a", "b")),
              "forEach should iterate over snapshot, got " + seen);
        check(list.size() == 3, "list should contain added element, size=" + list.size());
    }

    static void testEmptyListDoesNotInvokeAction() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        int[] count = new int[1];
        list.forEach(x -> count[0]++);
        check(count[0] == 0, "action should not be invoked for empty list");
    }
}
