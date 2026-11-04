/*
 * @test
 * @summary Test for ArrayList.remove(int) method, covering index bounds and concurrent modification.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveWithValidIndex();
        testRemoveWithNegativeIndex();
        testRemoveWithIndexEqualToSize();
        testRemoveConcurrentModification();
    }

    static void testRemoveWithValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String removed = list.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected removed element to be 'b'");
        }
        if (list.size() != 2) {
            throw new AssertionError("Expected list size to be 2 after removal");
        }
        if (!list.get(0).equals("a") || !list.get(1).equals("c")) {
            throw new AssertionError("List elements not as expected after removal");
        }
    }

    static void testRemoveWithNegativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        boolean threw = false;
        try {
            list.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    static void testRemoveWithIndexEqualToSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        boolean threw = false;
        try {
            list.remove(list.size());
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        }
    }

    static void testRemoveConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            for (String s : list) {
                list.remove(0);
            }
            throw new AssertionError("Expected ConcurrentModificationException to be thrown");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
