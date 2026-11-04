/* @test    @summary Test for LinkedList.remove(int) method handling index out of bounds and valid cases    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testRemoveWithValidIndex();
        testRemoveWithNegativeIndex();
        testRemoveWithIndexEqualToSize();
        testRemoveWithIndexGreaterThanSize();
    }

    static void testRemoveWithValidIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String removed = list.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected 'b', but got: " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("List size should be 2 after removal, but was: " + list.size());
        }
        if (!list.get(0).equals("a") || !list.get(1).equals("c")) {
            throw new AssertionError("List contents incorrect after removal");
        }
    }

    static void testRemoveWithNegativeIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add("y");
        boolean thrown = false;
        try {
            list.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    static void testRemoveWithIndexEqualToSize() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add("y");
        boolean thrown = false;
        try {
            list.remove(2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        }
    }

    static void testRemoveWithIndexGreaterThanSize() {
        LinkedList<String> list = new LinkedList<>();
        list.add("x");
        list.add("y");
        boolean thrown = false;
        try {
            list.remove(3);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        }
    }
}
