/*
 * @test
 * @summary Test java.util.Vector set(int index, E element) method
 * @run main VectorWitupTest_Pd13b19ffe199
 */

import java.util.Vector;

public class VectorWitupTest_Pd13b19ffe199 {
    public static void main(String[] args) {
        testSetValidIndex();
        testSetReturnsOldValue();
        testSetIndexOutOfBounds();
    }

    private static void testSetValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("zero");
        v.add("one");
        v.add("two");

        String old = v.set(1, "ONE");
        if (!"one".equals(old)) {
            throw new RuntimeException("Expected old value 'one', got: " + old);
        }
        if (!"ONE".equals(v.get(1))) {
            throw new RuntimeException("Expected new value 'ONE' at index 1, got: " + v.get(1));
        }
    }

    private static void testSetReturnsOldValue() {
        Vector<Integer> v = new Vector<>();
        v.add(10);
        v.add(20);

        Integer old = v.set(0, 100);
        if (!old.equals(10)) {
            throw new RuntimeException("Expected old value 10, got: " + old);
        }
        if (!v.get(0).equals(100)) {
            throw new RuntimeException("Expected new value 100 at index 0, got: " + v.get(0));
        }
    }

    private static void testSetIndexOutOfBounds() {
        Vector<String> v = new Vector<>();
        v.add("a");

        try {
            v.set(1, "b");
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }

        try {
            v.set(-1, "c");
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
