/*
 * @test
 * @summary Witup checks for CopyOnWriteArrayList.forEach null action handling, iteration order and snapshot semantics
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionOnEmptyListThrowsNPE();
        testIteratesAllElementsInOrderIncludingNull();
        testEmptyListInvokesNoAction();
        testSnapshotSemantics();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a"));
        try {
            list.forEach(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullActionOnEmptyListThrowsNPE() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try {
            list.forEach(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testIteratesAllElementsInOrderIncludingNull() {
        CopyOnWriteArrayList<String> list =
                new CopyOnWriteArrayList<>(new String[] {"a", null, "c"});
        List<String> seen = new ArrayList<>();
        list.forEach(x -> seen.add(x));
        check(seen.equals(Arrays.asList("a", null, "c")),
              "unexpected iteration result: " + seen);
    }

    static void testEmptyListInvokesNoAction() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        List<String> seen = new ArrayList<>();
        list.forEach(x -> seen.add(x));
        check(seen.isEmpty(), "action invoked on empty list");
    }

    static void testSnapshotSemantics() {
        CopyOnWriteArrayList<Integer> list =
                new CopyOnWriteArrayList<>(new Integer[] {1, 2, 3});
        List<Integer> seen = new ArrayList<>();
        list.forEach(x -> {
            seen.add(x);
            if (x == 1) {
                list.add(4);
            }
        });
        check(seen.equals(Arrays.asList(1, 2, 3)),
              "forEach did not iterate over snapshot: " + seen);
        check(list.size() == 4, "list was not modified as expected");
    }
}
