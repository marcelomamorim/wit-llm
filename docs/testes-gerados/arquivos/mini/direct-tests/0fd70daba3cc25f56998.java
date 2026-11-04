/*
 * @test
 * @summary Test LinkedList remove() method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testRemoveWithoutNextOrPrevious();
        testRemoveAfterNext();
        testRemoveAfterPrevious();
    }

    static void testRemoveWithoutNextOrPrevious() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        try {
            it.remove();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static void testRemoveAfterNext() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("Expected first element 'a', got: " + first);
        }
        it.remove();
        if (list.size() != 1 || !"b".equals(list.get(0))) {
            throw new RuntimeException("Remove after next failed: " + list);
        }
    }

    static void testRemoveAfterPrevious() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator(list.size());
        String last = it.previous();
        if (!"b".equals(last)) {
            throw new RuntimeException("Expected last element 'b', got: " + last);
        }
        it.remove();
        if (list.size() != 1 || !"a".equals(list.get(0))) {
            throw new RuntimeException("Remove after previous failed: " + list);
        }
    }
}
