/* @test    @summary Test removeFirst() method of LinkedList for normal and empty list scenarios    @run main LinkedListWitupTest_Paa999e19f64e */

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListWitupTest_Paa999e19f64e {

    public static void main(String[] args) {
        testRemoveFirstOnNonEmptyList();
        testRemoveFirstOnEmptyList();
    }

    private static void testRemoveFirstOnNonEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        String removed = list.removeFirst();
        if (!"A".equals(removed)) {
            throw new AssertionError("Expected 'A', but got: " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("List size should be 2 after removal, but was: " + list.size());
        }
        if (!"B".equals(list.get(0)) || !"C".equals(list.get(1))) {
            throw new AssertionError("Remaining list elements are incorrect: " + list);
        }
    }

    private static void testRemoveFirstOnEmptyList() {
        LinkedList<String> list = new LinkedList<>();
        boolean exceptionThrown = false;
        try {
            list.removeFirst();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException to be thrown when removing first element from empty list.");
        }
    }
}
