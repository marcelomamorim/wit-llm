/*
 * @test
 * @summary Test the LinkedList(Collection<? extends E> c) constructor for correct behavior.
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.Arrays;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testEmptyCollection();
        testCollectionWithElements();
        testNullCollection();
    }

    static void testEmptyCollection() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList());
        assert list.isEmpty() : "List should be empty when initialized with empty collection";
    }

    static void testCollectionWithElements() {
        LinkedList<Integer> original = new LinkedList<>();
        original.add(1);
        original.add(2);
        original.add(3);
        LinkedList<Integer> list = new LinkedList<>(original);
        assert list.size() == 3 : "List size should be 3";
        assert list.get(0) == 1 : "First element should be 1";
        assert list.get(1) == 2 : "Second element should be 2";
        assert list.get(2) == 3 : "Third element should be 3";
    }

    static void testNullCollection() {
        try {
            new LinkedList<>(null);
            assert false : "Constructor should throw NullPointerException when null is passed";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
