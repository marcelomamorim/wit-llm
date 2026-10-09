/* @test    @summary Test Vector.listIterator(int index) method for valid and invalid indices    @run main VectorWitupTest_P5b2bb2ce9d05 */

import java.util.Vector;
import java.util.ListIterator;

public class VectorWitupTest_P5b2bb2ce9d05 {

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
        int validIndex = 2;
        ListIterator<Integer> iterator = vector.listIterator(validIndex);
        boolean hasNext = iterator.hasNext();
        int nextIndex = iterator.nextIndex();
        int nextElement = iterator.next();
        assert hasNext : "Expected to have next element at index " + validIndex;
        assert nextIndex == validIndex : "Next index should be " + validIndex;
        assert nextElement == 3 : "Expected next element to be 3, but got " + nextElement;
    }

    private static void testListIteratorWithNegativeIndex() {
        Vector<Integer> vector = new Vector<>();
        vector.add(10);
        vector.add(20);
        try {
            vector.listIterator(-1);
            assert false : "Expected IndexOutOfBoundsException for negative index";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testListIteratorWithIndexGreaterThanSize() {
        Vector<Integer> vector = new Vector<>();
        vector.add(5);
        vector.add(15);
        int size = vector.size();
        try {
            vector.listIterator(size + 1);
            assert false : "Expected IndexOutOfBoundsException for index greater than size";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
