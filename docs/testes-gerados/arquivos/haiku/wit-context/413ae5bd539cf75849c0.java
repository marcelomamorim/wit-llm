/*
 * @test
 * @summary Witup-derived checks for CopyOnWriteArrayList.removeIf observable behavior and null filter handling
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testNullElementsAreHandled();
        testNullFilterThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemovesMatchingElements() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[] {1, 2, 3, 4, 5});
        boolean changed = list.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf should report change when elements match");
        check(list.equals(Arrays.asList(1, 3, 5)),
              "removeIf should remove only matching elements, got " + list);
    }

    static void testNoMatchReturnsFalse() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[] {1, 3, 5});
        boolean changed = list.removeIf(x -> x % 2 == 0);
        check(!changed, "removeIf should return false when nothing matches");
        check(list.equals(Arrays.asList(1, 3, 5)),
              "list should be unchanged when nothing matches, got " + list);
    }

    static void testNullElementsAreHandled() {
        List<String> source = Arrays.asList("a", null, "b");
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(source);
        boolean changed = list.removeIf(s -> s == null);
        check(changed, "removeIf should remove null elements");
        check(list.size() == 2, "expected size 2 after removing null, got " + list.size());
        check(list.equals(Arrays.asList("a", "b")),
              "unexpected contents after removing nulls: " + list);
    }

    static void testNullFilterThrowsNullPointerException() {
        CopyOnWriteArrayList<String> list =
            new CopyOnWriteArrayList<>(Arrays.asList("a"));
        boolean thrown = false;
        try {
            list.removeIf((Predicate<String>) null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "removeIf(null) should throw NullPointerException");
        check(list.equals(Arrays.asList("a")),
              "list should be unchanged after failed removeIf(null)");
    }
}
