/*
 * @test
 * @summary Test for ArrayList.remove(int) method, including out-of-bounds index.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

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
            throw new AssertionError("Expected removed element to be 'b', but was: " + removed);
        }
        if (list.size() != 2) {
            throw new AssertionError("Expected list size 2, but was: " + list.size());
        }
        if (!list.get(0).equals("a") || !list.get(1).equals("c")) {
            throw new AssertionError("List elements mismatch after removal");
        }
    }

    private static void testRemoveNegativeIndex() {
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

    private static void testRemoveIndexEqualSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("m");
        list.add("n");
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

    private static void testRemoveIndexGreaterThanSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("p");
        boolean threw = false;
        try {
            list.remove(10);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        }
    }
}
