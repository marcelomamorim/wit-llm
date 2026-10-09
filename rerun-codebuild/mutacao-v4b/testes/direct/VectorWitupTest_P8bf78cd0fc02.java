/* @test
   @summary Tests Vector.listIterator(int index) for valid and invalid indices.
   @run main VectorWitupTest_P8bf78cd0fc02
 */
import java.util.Vector;
import java.util.ListIterator;
public class VectorWitupTest_P8bf78cd0fc02 {
    public static void main(String[] args) {
        testListIteratorValidIndex();
        testListIteratorNegativeIndex();
        testListIteratorIndexGreaterThanSize();
    }

    private static void testListIteratorValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        ListIterator<String> iterator = vector.listIterator(1);
        boolean hasNext = iterator.hasNext();
        String nextElement = hasNext ? iterator.next() : null;
        if (nextElement == null || !nextElement.equals("b")) {
            throw new AssertionError("Expected next element to be 'b', but got: " + nextElement);
        }
    }

    private static void testListIteratorNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        try {
            vector.listIterator(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testListIteratorIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("y");
        vector.add("z");
        int size = vector.size();
        try {
            vector.listIterator(size + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
