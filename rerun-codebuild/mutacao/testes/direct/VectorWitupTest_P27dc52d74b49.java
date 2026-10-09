/* @test    @summary Test Vector.listIterator(int index) for valid and invalid indices    @run main VectorWitupTest_P27dc52d74b49 */

import java.util.Vector;
import java.util.ListIterator;

public class VectorWitupTest_P27dc52d74b49 {

    public static void main(String[] args) {
        testListIteratorValidIndex();
        testListIteratorNegativeIndex();
        testListIteratorIndexGreaterThanSize();
    }

    private static void testListIteratorValidIndex() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        try {
            ListIterator<Integer> it = vector.listIterator(1);
            if (it.next() != 2) {
                throw new AssertionError("Expected first element to be 2");
            }
            if (!it.hasNext()) {
                throw new AssertionError("Iterator should have next element");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid index: " + e);
        }
    }

    private static void testListIteratorNegativeIndex() {
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

    private static void testListIteratorIndexGreaterThanSize() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        boolean thrown = false;
        try {
            vector.listIterator(4);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        }
    }
}
