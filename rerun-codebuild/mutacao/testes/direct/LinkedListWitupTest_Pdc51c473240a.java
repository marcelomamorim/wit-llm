/* @test    @summary Test removeFirst() method of LinkedList for normal and empty list scenarios    @run main LinkedListWitupTest_Pdc51c473240a */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_Pdc51c473240a {

    public static void main(String[] args) {
        testRemoveFirstWithElements();
        testRemoveFirstEmptyList();
    }

    private static void testRemoveFirstWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        String removed = list.removeFirst();
        if (!"A".equals(removed)) {
            throw new AssertionError("Expected 'A', but got: " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("List size should be 2 after removal, but is: " + list.size());
        }
        if (!list.getFirst().equals("B")) {
            throw new AssertionError("First element should be 'B', but is: " + list.getFirst());
        }
    }

    private static void testRemoveFirstEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        boolean exceptionThrown = false;
        try {
            list.removeFirst();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown when removing from empty list");
        }
    }
}
