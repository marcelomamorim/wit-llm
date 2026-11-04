/*
 * @test
 * @summary Tests CopyOnWriteArrayList.removeIf null filter handling and basic behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullFilterThrowsNpe() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b"});
        try {
            list.removeIf(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(list.size() == 2, "list must be unchanged after NPE");
    }

    static void testRemoveMatchingElements() {
        CopyOnWriteArrayList<Integer> list =
            new CopyOnWriteArrayList<>(new Integer[] {1, 2, 3, 4, 5, 6});
        boolean removed = list.removeIf(x -> x % 2 == 0);
        check(removed, "removeIf should report true when elements are removed");
        check(list.equals(Arrays.asList(1, 3, 5)), "unexpected contents: " + list);
    }

    static void testNoMatchReturnsFalse() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {"a", "b"});
        boolean removed = list.removeIf(s -> s.equals("z"));
        check(!removed, "removeIf should return false when nothing matches");
        check(list.equals(Arrays.asList("a", "b")), "list must be unchanged");
    }

    static void testEmptyListReturnsFalse() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        check(!list.removeIf(s -> true), "empty list removeIf must return false");
        check(list.isEmpty(), "list must remain empty");
    }

    static void testRemoveAllWithNullElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[] {null, "x", null});
        boolean removed = list.removeIf(s -> s == null);
        check(removed, "nulls should be removable");
        List<String> expected = Arrays.asList("x");
        check(list.equals(expected), "unexpected contents: " + list);
    }

    public static void main(String[] args) {
        testNullFilterThrowsNpe();
        testRemoveMatchingElements();
        testNoMatchReturnsFalse();
        testEmptyListReturnsFalse();
        testRemoveAllWithNullElements();
    }
}
