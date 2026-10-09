/*
 * @test
 * @summary Test java.util.Vector remove(int index) method behavior
 * @run main VectorWitupTest_Pcdc444623f99
 */

import java.util.Vector;

public class VectorWitupTest_Pcdc444623f99 {
    public static void main(String[] args) {
        testRemoveValidIndex();
        testRemoveLastElement();
        testRemoveEmptyVector();
        testRemoveIndexOutOfBounds();
    }

    private static void testRemoveValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("zero");
        v.add("one");
        v.add("two");

        String removed = v.remove(1);
        if (!"one".equals(removed)) {
            throw new RuntimeException("Expected removed element 'one', got: " + removed);
        }
        if (v.size() != 2) {
            throw new RuntimeException("Expected size 2 after removal, got: " + v.size());
        }
        if (!"two".equals(v.get(1))) {
            throw new RuntimeException("Expected element at index 1 to be 'two' after removal, got: " + v.get(1));
        }
    }

    private static void testRemoveLastElement() {
        Vector<Integer> v = new Vector<>();
        v.add(10);
        v.add(20);

        Integer removed = v.remove(1);
        if (!Integer.valueOf(20).equals(removed)) {
            throw new RuntimeException("Expected removed element 20, got: " + removed);
        }
        if (v.size() != 1) {
            throw new RuntimeException("Expected size 1 after removal, got: " + v.size());
        }
        if (!Integer.valueOf(10).equals(v.get(0))) {
            throw new RuntimeException("Expected element at index 0 to be 10 after removal, got: " + v.get(0));
        }
    }

    private static void testRemoveEmptyVector() {
        Vector<Object> v = new Vector<>();
        try {
            v.remove(0);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for remove on empty vector");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRemoveIndexOutOfBounds() {
        Vector<String> v = new Vector<>();
        v.add("only");
        try {
            v.remove(1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            v.remove(-1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
