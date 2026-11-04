/*
 * @test
 * @summary Behavioral tests for CopyOnWriteArrayList.forEach
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsElementsInOrder();
        testEmptyListInvokesNoActions();
        testNullElementsArePassed();
        testSnapshotIgnoresConcurrentAdds();
        testActionExceptionPropagates();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a"});
        try {
            list.forEach(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testVisitsElementsInOrder() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(new String[]{"x", "y", "z"});
        List<String> seen = new ArrayList<>();
        list.forEach(seen::add);
        check(seen.size() == 3, "wrong count: " + seen);
        check(seen.get(0).equals("x"), "index 0: " + seen);
        check(seen.get(1).equals("y"), "index 1: " + seen);
        check(seen.get(2).equals("z"), "index 2: " + seen);
    }

    static void testEmptyListInvokesNoActions() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        int[] calls = {0};
        list.forEach(e -> calls[0]++);
        check(calls[0] == 0, "action invoked on empty list");
    }

    static void testNullElementsArePassed() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(new String[]{null, "b", null});
        List<String> seen = new ArrayList<>();
        list.forEach(seen::add);
        check(seen.size() == 3, "wrong count with nulls: " + seen);
        check(seen.get(0) == null, "first element should be null");
        check("b".equals(seen.get(1)), "second element should be b");
        check(seen.get(2) == null, "third element should be null");
    }

    static void testSnapshotIgnoresConcurrentAdds() {
        CopyOnWriteArrayList<Integer> list =
                new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        int[] count = {0};
        list.forEach(x -> {
            count[0]++;
            list.add(99);
        });
        check(count[0] == 3, "iteration should use snapshot, count=" + count[0]);
        check(list.size() == 6, "list should have 6 elements, size=" + list.size());
    }

    static void testActionExceptionPropagates() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a"});
        try {
            list.forEach(e -> {
                throw new IllegalStateException("boom");
            });
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
