/* @test    @summary Test LinkedList.remove(int index) method for correct removal and exception handling    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveInvalidIndex();
    }

    static void testRemoveValidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String removed = list.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected 'b', but got: " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("List size should be 2 after removal, but is: " + list.size());
        }
        if (!list.get(0).equals("a") || !list.get(1).equals("c")) {
            throw new AssertionError("Remaining elements are incorrect: " + list);
        }
    }

    static void testRemoveInvalidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add("y");
        try {
            list.remove(5);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 5");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            list.remove(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
