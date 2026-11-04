/* @test    @summary Test ArrayList.add(int, E) method for correct insertion and exception handling    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddAtValidIndex();
        testAddAtNegativeIndex();
        testAddAtIndexGreaterThanSize();
    }

    private static void testAddAtValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add(1, "x");
        assert list.get(1).equals("x") : "Element at index 1 should be 'x'";
        assert list.size() == 4 : "List size should be 4";
        assert list.get(2).equals("b") : "Element at index 2 should be 'b'";
    }

    private static void testAddAtNegativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.add(-1, "b");
            assert false : "Adding at negative index should throw IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testAddAtIndexGreaterThanSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.add(2, "b");
            assert false : "Adding at index greater than size should throw IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
