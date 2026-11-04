/*
 * @test
 * @summary Verifies ArrayList.removeAll(Collection) null handling and observable removal behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testRemoveAllRemovesMatchingElements();
        testRemoveAllReturnsFalseWhenNothingRemoved();
        testRemoveAllOnEmptyList();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNullElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullCollectionThrowsNPE() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        boolean thrown = false;
        try {
            list.removeAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeAll(null) should throw NullPointerException");
        check(list.equals(Arrays.asList("a", "b")), "list must be unchanged after NPE");
    }

    static void testRemoveAllRemovesMatchingElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b", "a", "d"));
        boolean changed = list.removeAll(Arrays.asList("a", "c"));
        check(changed, "removeAll should return true when elements are removed");
        check(list.equals(Arrays.asList("b", "d")), "unexpected content: " + list);
        check(list.size() == 2, "size should be updated to 2");
    }

    static void testRemoveAllReturnsFalseWhenNothingRemoved() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("x", "y"));
        boolean changed = list.removeAll(Arrays.asList("z"));
        check(!changed, "removeAll should return false when no element matches");
        check(list.equals(Arrays.asList("x", "y")), "list should be unchanged");
    }

    static void testRemoveAllOnEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        boolean changed = list.removeAll(Arrays.asList("a"));
        check(!changed, "removeAll on empty list should return false");
        check(list.isEmpty(), "list should remain empty");
    }

    static void testRemoveAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        boolean changed = list.removeAll(Collections.<String>emptyList());
        check(!changed, "removeAll with empty collection should return false");
        check(list.equals(Arrays.asList("a", "b")), "list should be unchanged");
    }

    static void testRemoveAllWithNullElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(null, "x", null));
        boolean changed = list.removeAll(Arrays.asList((String) null));
        check(changed, "removeAll should remove null elements");
        check(list.equals(Arrays.asList("x")), "unexpected content after null removal: " + list);
    }
}
