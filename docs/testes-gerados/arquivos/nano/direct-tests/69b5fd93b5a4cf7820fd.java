/* @test    @summary Test addAll(Collection<? extends E> c) method of LinkedList for correctness and regressions    @run main LinkedListWitupTest */

import java.util.LinkedList;
import java.util.Collection;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testAddAllWithEmptyCollection();
        testAddAllWithNonEmptyCollection();
        testAddAllWithNullCollection();
    }

    static void testAddAllWithEmptyCollection() {
        LinkedList<String> list = new LinkedList<>();
        Collection<String> empty = new LinkedList<>();
        boolean result = list.addAll(empty);
        if (!result || list.size() != 0) {
            throw new AssertionError("Failed addAll with empty collection") ;
        }
    }

    static void testAddAllWithNonEmptyCollection() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        Collection<String> c = new LinkedList<>();
        c.add("c");
        c.add("d");
        boolean result = list.addAll(c);
        if (!result || list.size() != 4 || !list.get(2).equals("c") || !list.get(3).equals("d")) {
            throw new AssertionError("Failed addAll with non-empty collection") ;
        }
    }

    static void testAddAllWithNullCollection() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.addAll(null);
            throw new AssertionError("Expected NullPointerException") ;
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
