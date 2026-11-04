/*
 * @test
 * @summary Behavioral checks for LinkedList.addAll(Collection), which appends the collection at the end of the list
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testAppendToEmptyList();
        testAppendPreservesOrderAndReturnsTrue();
        testEmptyCollectionReturnsFalseAndLeavesListUnchanged();
        testSelfAddAppendsCopyOfElements();
        testNullCollectionThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAppendToEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        boolean changed = list.addAll(Arrays.asList("x", "y"));
        check(changed, "addAll on non-empty collection must return true");
        check(list.size() == 2, "size expected 2 but was " + list.size());
        check(list.equals(Arrays.asList("x", "y")), "unexpected contents: " + list);
    }

    static void testAppendPreservesOrderAndReturnsTrue() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        boolean changed = list.addAll(Arrays.asList("b", "c"));
        check(changed, "addAll must return true when elements are added");
        check(list.equals(Arrays.asList("a", "b", "c")), "unexpected contents: " + list);
        check(list.getLast().equals("c"), "last element expected c");
    }

    static void testEmptyCollectionReturnsFalseAndLeavesListUnchanged() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        boolean changed = list.addAll(Collections.<String>emptyList());
        check(!changed, "addAll with empty collection must return false");
        check(list.equals(Arrays.asList("a")), "list must be unchanged, was " + list);
    }

    static void testSelfAddAppendsCopyOfElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        boolean changed = list.addAll(list);
        check(changed, "self addAll must return true");
        check(list.equals(Arrays.asList("a", "b", "a", "b")), "unexpected contents: " + list);
    }

    static void testNullCollectionThrowsNullPointerException() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        try {
            list.addAll(null);
            throw new RuntimeException("expected NullPointerException for null collection");
        } catch (NullPointerException expected) {
            // expected
        }
        check(list.equals(Arrays.asList("a")), "list must be unchanged after failed addAll, was " + list);
    }
}
