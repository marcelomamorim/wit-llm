/*
 * @test
 * @summary Test LinkedList.next() method behavior
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        ListIterator<String> itr = list.listIterator(list.size());

        // The tested method is LinkedList.next(), which calls itr.previous() internally.
        // We test that next() returns elements in reverse order when iterator is at the end.

        // First call to next() should return "c"
        String first = list.next();
        if (!"c".equals(first)) {
            throw new AssertionError("Expected 'c' but got: " + first);
        }

        // Second call to next() should return "b"
        String second = list.next();
        if (!"b".equals(second)) {
            throw new AssertionError("Expected 'b' but got: " + second);
        }

        // Third call to next() should return "a"
        String third = list.next();
        if (!"a".equals(third)) {
            throw new AssertionError("Expected 'a' but got: " + third);
        }

        // After all elements are returned, calling next() should throw NoSuchElementException
        boolean threw = false;
        try {
            list.next();
        } catch (java.util.NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException after all elements consumed");
        }
    }

    private final LinkedList<String> list = new LinkedList<>();
    private final ListIterator<String> itr;

    public LinkedListWitupTest() {
        list.add("a");
        list.add("b");
        list.add("c");
        itr = list.listIterator(list.size());
    }

    // The next() method under test calls itr.previous()
    public String next() {
        return itr.previous();
    }
}
