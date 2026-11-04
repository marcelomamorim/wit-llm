/*
 * @test
 * @summary Test for ArrayList.set(int, E) method, focusing on index bounds.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSetWithValidIndex();
        testSetWithNegativeIndex();
        testSetWithIndexEqualToSize();
    }

    private static void testSetWithValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String oldValue = list.set(1, "c");
        if (!"b".equals(oldValue)) {
            throw new AssertionError("Expected old value 'b', but got: " + oldValue);
        }
        if (!"c".equals(list.get(1))) {
            throw new AssertionError("Expected new value 'c' at index 1, but got: " + list.get(1));
        }
    }

    private static void testSetWithNegativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        boolean threw = false;
        try {
            list.set(-1, "x");
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testSetWithIndexEqualToSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        boolean threw = false;
        try {
            list.set(list.size(), "x");
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        }
    }
}
