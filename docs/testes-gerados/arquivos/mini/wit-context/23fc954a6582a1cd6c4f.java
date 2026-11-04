/*
 * @test
 * @summary Test java.util.ArrayList.remove(int) for index bounds and normal removal
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Objects;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveNegativeIndex();
        testRemoveIndexEqualSize();
        testRemoveIndexGreaterThanSize();
    }

    private static void testRemoveValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        String removed = list.remove(1);
        if (!"b".equals(removed)) {
            throw new AssertionError("Expected removed element 'b', got: " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("Expected size 2 after removal, got: " + list.size());
        }
        if (!"a".equals(list.get(0)) || !"c".equals(list.get(1))) {
            throw new AssertionError("List elements after removal are incorrect");
        }
    }

    private static void testRemoveNegativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");

        try {
            list.remove(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRemoveIndexEqualSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");

        try {
            list.remove(list.size()); // index == size
            throw new AssertionError("Expected IndexOutOfBoundsException for index == size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRemoveIndexGreaterThanSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");

        try {
            list.remove(list.size() + 1); // index > size
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
