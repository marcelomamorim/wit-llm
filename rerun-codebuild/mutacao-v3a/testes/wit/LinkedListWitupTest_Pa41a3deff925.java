/* @test    @summary Test removeLast method for LinkedList, including empty list case    @run main LinkedListWitupTest_Pa41a3deff925 */

import java.util.LinkedList;

public class LinkedListWitupTest_Pa41a3deff925 {

    public static void main(String[] args) {
        testRemoveLastEmpty();
        testRemoveLastWithElements();
    }

    static void testRemoveLastEmpty() {
        LinkedList<Object> list = new LinkedList<>();
        boolean threwException = false;
        try {
            list.removeLast();
        } catch (NoSuchElementException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NoSuchElementException when removing last from empty list");
        }
    }

    static void testRemoveLastWithElements() {
        LinkedList<String> list = new LinkedList<>();
        list.add("A");
        list.add("B");
        String last = list.removeLast();
        if (!"B".equals(last)) {
            throw new AssertionError("Expected last element to be 'B'");
        }
        if (list.size() != 1) {
            throw new AssertionError("List size should be 1 after removeLast");
        }
        if (!list.getFirst().equals("A")) {
            throw new AssertionError("Remaining element should be 'A'");
        }
    }
}
