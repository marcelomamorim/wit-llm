/* @test    @summary Test removeElementAt for Vector, including boundary conditions and exception paths    @run main VectorWitupTest_P70396cbf4eaf */

import java.util.Vector;

public class VectorWitupTest_P70396cbf4eaf {
    public static void main(String[] args) {
        testRemoveElementAtValidIndex();
        testRemoveElementAtIndexEqualToSize();
        testRemoveElementAtNegativeIndex();
        System.out.println("All tests passed.");
    }

    private static void testRemoveElementAtValidIndex() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);
        v.removeElementAt(1); // remove element at index 1
        if (v.size() != 2) {
            throw new AssertionError("Expected size 2, got " + v.size());
        }
        if (!v.get(0).equals(1) || !v.get(1).equals(3)) {
            throw new AssertionError("Unexpected vector contents after removal");
        }
    }

    private static void testRemoveElementAtIndexEqualToSize() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);
        try {
            v.removeElementAt(v.size()); // index == size, should throw
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index == size");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRemoveElementAtNegativeIndex() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);
        try {
            v.removeElementAt(-1); // negative index, should throw
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
