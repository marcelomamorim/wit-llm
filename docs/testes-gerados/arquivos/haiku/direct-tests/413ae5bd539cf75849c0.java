/*
 * @test
 * @summary Tests CopyOnWriteArrayList.removeIf null check, return value, null elements and snapshot iterator behavior
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFilterThrowsNpe() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));
        try {
            list.removeIf(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(list.equals(Arrays.asList(1, 2, 3)), "list must be unchanged after NPE");
    }

    static void testRemovesMatchingElements() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        boolean changed = list.removeIf(x -> x % 2 == 0);
        check(changed, "removeIf must return true when elements are removed");
        check(list.equals(Arrays.asList(1, 3, 5)), "unexpected list content: " + list);
    }

    static void testNoMatchReturnsFalse() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 3, 5));
        boolean changed = list.removeIf(x -> x % 2 == 0);
        check(!changed, "removeIf must return false when nothing matches");
        check(list.equals(Arrays.asList(1, 3, 5)), "list must be unchanged");
    }

    static void testEmptyListReturnsFalse() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        boolean changed = list.removeIf(x -> true);
        check(!changed, "removeIf on empty list must return false");
        check(list.isEmpty(), "list must remain empty");
    }

    static void testRemoveAll() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("a", "b"));
        boolean changed = list.removeIf(x -> true);
        check(changed, "removeIf must return true when all elements are removed");
        check(list.isEmpty(), "list must be empty");
    }

    static void testNullElementsAreRemovable() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList(null, "a", null));
        boolean changed = list.removeIf(x -> x == null);
        check(changed, "removeIf must remove null elements");
        check(list.size() == 1, "expected size 1, got " + list.size());
        check("a".equals(list.get(0)), "remaining element must be a");
    }

    static void testSnapshotIteratorUnaffected() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3, 4));
        Iterator<Integer> it = list.iterator();
        list.removeIf(x -> x > 2);
        List<Integer> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }
        check(seen.equals(Arrays.asList(1, 2, 3, 4)), "snapshot iterator must see original elements, got " + seen);
        check(list.equals(Arrays.asList(1, 2)), "list content after removeIf is wrong");
    }

    public static void main(String[] args) {
        testNullFilterThrowsNpe();
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testEmptyListReturnsFalse();
        testRemoveAll();
        testNullElementsAreRemovable();
        testSnapshotIteratorUnaffected();
    }
}
