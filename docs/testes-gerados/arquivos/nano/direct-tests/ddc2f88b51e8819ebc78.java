/*
 * @test
 * @summary Test ArrayList.add(int, E) method for correctness and boundary conditions
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddAtBeginning();
        testAddAtEnd();
        testAddInMiddle();
        testAddWithInvalidIndex();
    }

    static void testAddAtBeginning() {
        ArrayList<String> list = new ArrayList<>();
        list.add("b");
        list.add(0, "a");
        assert list.get(0).equals("a") : "Failed to add at beginning";
        assert list.size() == 2 : "Size mismatch after add at beginning";
    }

    static void testAddAtEnd() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add(1, "b");
        assert list.get(1).equals("b") : "Failed to add at end";
        assert list.size() == 2 : "Size mismatch after add at end";
    }

    static void testAddInMiddle() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("c");
        list.add(1, "b");
        assert list.get(1).equals("b") : "Failed to add in middle";
        assert list.get(2).equals("c") : "Order incorrect after add in middle";
        assert list.size() == 3 : "Size mismatch after add in middle";
    }

    static void testAddWithInvalidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        boolean exceptionThrown = false;
        try {
            list.add(5, "b");
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for invalid index";
    }
}
