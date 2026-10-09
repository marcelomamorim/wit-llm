/* @test    @summary Test removeLast() method of LinkedList for normal and empty list scenarios    @run main LinkedListWitupTest_P4dd69c7b62d2 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_P4dd69c7b62d2 {

    public static void main(String[] args) {
        testRemoveLastOnNonEmptyList();
        testRemoveLastOnEmptyList();
    }

    static void testRemoveLastOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("first");
        list.add("second");
        String removed = list.removeLast();
        assert "second".equals(removed) : "Expected 'second', got " + removed;
        assert list.size() == 1 : "Expected size 1, got " + list.size();
        assert list.getFirst().equals("first") : "Expected 'first', got " + list.getFirst();
    }

    static void testRemoveLastOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.removeLast();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
