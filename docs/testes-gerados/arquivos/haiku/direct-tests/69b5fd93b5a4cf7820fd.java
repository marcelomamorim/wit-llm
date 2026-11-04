/*
 * @test
 * @summary LinkedList.addAll(Collection) appends the elements of the given collection at the end of the list
 * @run main LinkedListWitupTest
 */

import java.util.Arrays;
import java.util.LinkedList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        appendsAtEnd();
        emptyCollectionReturnsFalse();
        nonEmptyCollectionReturnsTrue();
        selfAddDuplicatesElements();
        nullElementsArePreserved();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void appendsAtEnd() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 2));
        boolean changed = list.addAll(Arrays.asList(3, 4));
        check(changed, "addAll should return true for a non-empty collection");
        check(list.size() == 4, "size should be 4 after appending two elements");
        check(list.equals(Arrays.asList(1, 2, 3, 4)), "elements should be appended in order at the end");
    }

    static void emptyCollectionReturnsFalse() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("a"));
        boolean changed = list.addAll(new LinkedList<String>());
        check(!changed, "addAll should return false for an empty collection");
        check(list.size() == 1, "list should be unchanged when adding an empty collection");
        check("a".equals(list.get(0)), "existing element should be unchanged");
    }

    static void nonEmptyCollectionReturnsTrue() {
        LinkedList<String> list = new LinkedList<>();
        boolean changed = list.addAll(Arrays.asList("x"));
        check(changed, "addAll on empty list with one element should return true");
        check(list.size() == 1, "size should be 1");
        check("x".equals(list.get(0)), "element should be x");
    }

    static void selfAddDuplicatesElements() {
        LinkedList<Integer> list = new LinkedList<>(Arrays.asList(1, 2));
        boolean changed = list.addAll(list);
        check(changed, "adding a list to itself should return true");
        check(list.equals(Arrays.asList(1, 2, 1, 2)), "self addAll should duplicate the elements");
    }

    static void nullElementsArePreserved() {
        LinkedList<String> list = new LinkedList<>();
        boolean changed = list.addAll(Arrays.asList(null, "b"));
        check(changed, "addAll with null elements should return true");
        check(list.size() == 2, "size should be 2");
        check(list.get(0) == null, "first element should be null");
        check("b".equals(list.get(1)), "second element should be b");
    }
}
