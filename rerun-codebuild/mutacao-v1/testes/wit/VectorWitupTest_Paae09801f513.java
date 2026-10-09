/* @test    @summary Tests for java.util.Vector.listIterator(int index) method.    @run main VectorWitupTest_Paae09801f513 */

import java.util.Vector;
import java.util.ListIterator;

public class VectorWitupTest_Paae09801f513 {

    public static void main(String[] args) {
        testListIteratorWithValidIndex();
        testListIteratorWithNegativeIndex();
        testListIteratorWithIndexGreaterThanSize();
    }

    private static void testListIteratorWithValidIndex() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        try {
            ListIterator<Integer> it = vector.listIterator(1);
            if (it == null) {
                throw new AssertionError("Expected non-null ListIterator");
            }
            if (!it.hasNext()) {
                throw new AssertionError("Iterator should have next element at index 1");
            }
            int val = it.next();
            if (val != 2) {
                throw new AssertionError("Expected next element to be 2, but got " + val);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected IndexOutOfBoundsException for valid index", e);
        }
    }

    private static void testListIteratorWithNegativeIndex() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        boolean thrown = false;
        try {
            vector.listIterator(-1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testListIteratorWithIndexGreaterThanSize() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        boolean thrown = false;
        try {
            vector.listIterator(4); // size is 3, index 4 is invalid
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        }
    }
}
