/*
 * @test
 * @summary Test java.util.ArrayList add(int index, E element) method
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
        list.add(0, "first");
        if (!"first".equals(list.get(0))) {
            throw new RuntimeException("Failed to add element at beginning");
        }
        if (list.size() != 1) {
            throw new RuntimeException("Size should be 1 after add at beginning");
        }
    }

    private static void testAddAtMiddle() {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "a");
        list.add(1, "c");
        list.add(1, "b");
        if (!"b".equals(list.get(1))) {
            throw new RuntimeException("Failed to add element at middle");
        }
        if (list.size() != 3) {
            throw new RuntimeException("Size should be 3 after add at middle");
        }
    }

    private static void testAddAtEnd() {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "a");
        list.add(1, "b");
        list.add(2, "c");
        if (!"c".equals(list.get(2))) {
            throw new RuntimeException("Failed to add element at end");
        }
        if (list.size() != 3) {
            throw new RuntimeException("Size should be 3 after add at end");
        }
    }

    private static void testAddNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, null);
        if (list.size() != 1) {
            throw new RuntimeException("Size should be 1 after adding null element");
        }
        if (list.get(0) != null) {
            throw new RuntimeException("Element at index 0 should be null");
        }
    }

    private static void testAddIndexOutOfBounds() {
        ArrayList<String> list = new ArrayList<>();
        boolean caught = false;
        try {
            list.add(1, "fail");
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new RuntimeException("Expected IndexOutOfBoundsException for add with invalid index");
        }
    }
}
