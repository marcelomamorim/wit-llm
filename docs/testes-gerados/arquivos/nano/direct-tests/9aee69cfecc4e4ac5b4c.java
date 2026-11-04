/* @test    @summary Test ArrayList.remove() method for correct behavior and exception handling    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveWithValidIndex();
        testRemoveWithInvalidIndex();
        testRemoveEmptyList();
    }

    private static void testRemoveWithValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.remove();
        // After remove(), list should have size 2 and contain "a" and "b"
        if (list.size() != 2) {
            throw new AssertionError("Expected size 2, but got " + list.size());
        }
        if (!list.get(0).equals("a") || !list.get(1).equals("b")) {
            throw new AssertionError("List contents mismatch after remove()\n" + list);
        }
    }

    private static void testRemoveWithInvalidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        list.remove(); // remove() should remove last element
        list.remove(); // remove() again
        list.remove(); // remove() again, should throw IndexOutOfBoundsException
        try {
            list.remove();
            throw new AssertionError("Expected IndexOutOfBoundsException was not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected exception
        }
    }

    private static void testRemoveEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list.remove();
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // expected exception
        }
    }
}
