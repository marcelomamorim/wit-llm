/*
 * @test
 * @summary Test java.util.ArrayList add(int, E) method behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testAddAtBeginning();
        testAddAtMiddle();
        testAddAtEnd();
        testAddNullElement();
        testAddIndexOutOfBounds();
    }

    private static void testAddAtBeginning() {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "a");
        if (!"a".equals(list.get(0)) || list.size() != 1) {
            throw new AssertionError("Add at beginning failed");
        }
    }

    private static void testAddAtMiddle() {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "a");
        list.add(1, "c");
        list.add(1, "b");
        if (!"b".equals(list.get(1)) || !"c".equals(list.get(2)) || list.size() != 3) {
            throw new AssertionError("Add at middle failed");
        }
    }

    private static void testAddAtEnd() {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "a");
        list.add(1, "b");
        list.add(2, "c");
        if (!"c".equals(list.get(2)) || list.size() != 3) {
            throw new AssertionError("Add at end failed");
        }
    }

    private static void testAddNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, null);
        if (list.size() != 1 || list.get(0) != null) {
            throw new AssertionError("Add null element failed");
        }
    }

    private static void testAddIndexOutOfBounds() {
        ArrayList<String> list = new ArrayList<>();
        boolean thrown = false;
        try {
            list.add(1, "x"); // index > size
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index > size");
        }
        thrown = false;
        try {
            list.add(-1, "x"); // negative index
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }
}
