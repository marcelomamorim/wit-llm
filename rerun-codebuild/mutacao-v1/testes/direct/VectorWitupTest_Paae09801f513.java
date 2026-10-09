/* @test    @summary Test Vector.listIterator(int index) for valid and invalid indices    @run main VectorWitupTest_Paae09801f513 */

import java.util.Vector;
import java.util.ListIterator;

public class VectorWitupTest_Paae09801f513 {

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
            if (it == null) {
                throw new AssertionError("listIterator returned null for valid index");
            }
            if (!it.hasNext()) {
                throw new AssertionError("Iterator should have next element at index 1");
            }
            Integer value = it.next();
            if (value != 2) {
                throw new AssertionError("Expected value 2 at index 1, got " + value);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected IndexOutOfBoundsException for valid index", e);
        }
    }

    private static void testListIteratorNegativeIndex() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        try {
            vector.listIterator(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testListIteratorIndexGreaterThanSize() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        try {
            vector.listIterator(4);
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
