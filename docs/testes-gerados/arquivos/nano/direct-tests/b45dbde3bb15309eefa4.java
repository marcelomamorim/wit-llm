/* @test    @summary Test ArrayList.add(E e) method for correct addition of elements and exception handling.    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddElement();
        testAddNullElement();
        testAddAfterRemoval();
    }

    private static void testAddElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("element1");
        if (list.size() != 1) {
            throw new AssertionError("Expected size 1 after adding one element");
        }
        if (!"element1".equals(list.get(0))) {
            throw new AssertionError("Element at index 0 should be 'element1'");
        }
    }

    private static void testAddNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        if (list.size() != 1) {
            throw new AssertionError("Expected size 1 after adding null element");
        }
        if (list.get(0) != null) {
            throw new AssertionError("Element at index 0 should be null");
        }
    }

    private static void testAddAfterRemoval() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.remove(0);
        list.add("c");
        if (list.size() != 2) {
            throw new AssertionError("Expected size 2 after removals and additions");
        }
        if (!"b".equals(list.get(0)) || !"c".equals(list.get(1))) {
            throw new AssertionError("Elements should be 'b' and 'c' in order");
        }
    }
}
