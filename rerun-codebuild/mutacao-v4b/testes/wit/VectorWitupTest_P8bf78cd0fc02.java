/* @test
   @summary Tests for java.util.Vector class, focusing on listIterator method.
   @run main VectorWitupTest_P8bf78cd0fc02
 */
import java.util.Vector;
import java.util.ListIterator;
public class VectorWitupTest_P8bf78cd0fc02 {
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
        int index = 1;
        ListIterator<Integer> it = vector.listIterator(index);
        if (it.next() != 2) {
            throw new AssertionError("Expected first element to be 2");
        }
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        if (it.next() != 3) {
            throw new AssertionError("Expected second element to be 3");
        }
    }
    private static void testListIteratorWithNegativeIndex() {
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
    private static void testListIteratorWithIndexGreaterThanSize() {
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
