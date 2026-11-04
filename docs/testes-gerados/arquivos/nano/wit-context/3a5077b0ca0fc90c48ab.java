/*
 * @test
 * @summary Tests for ArrayList.get(int index)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testGetWithValidIndex();
        testGetWithNegativeIndex();
        testGetWithIndexEqualToSize();
    }

    private static void testGetWithValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String result = list.get(1);
        if (!"b".equals(result)) {
            throw new AssertionError("Expected 'b', but got: " + result);
        }
    }

    private static void testGetWithNegativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            list.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetWithIndexEqualToSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            list.get(3);
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
