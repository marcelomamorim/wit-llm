/* @test    @summary Test ArrayList.get method for valid and invalid indices    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testGetValidIndex();
        testGetNegativeIndex();
        testGetIndexEqualToSize();
    }

    private static void testGetValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String element = list.get(1);
        if (!"b".equals(element)) {
            throw new AssertionError("Expected 'b' at index 1, but got: " + element);
        }
    }

    private static void testGetNegativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        try {
            list.get(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testGetIndexEqualToSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");
        list.add("baz");
        try {
            list.get(list.size());
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
