/*
 * @test
 * @summary Test removeFirst() method of LinkedList.
 * @run main LinkedListWitupTest_P7bbfa5541a43
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_P7bbfa5541a43 {

    public static void main(String[] args) {
        testRemoveFirstWithElements();
        testRemoveFirstEmpty();
    }

    private static void testRemoveFirstWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String removed = list.removeFirst();
        assert removed.equals("a") : "Expected 'a', got " + removed;
        assert list.size() == 2 : "Expected size 2, got " + list.size();
        assert list.getFirst().equals("b") : "Expected 'b', got " + list.getFirst();
    }

    private static void testRemoveFirstEmpty() {
        LinkedList<String> list = new LinkedList<>();
        try {
            list.removeFirst();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
