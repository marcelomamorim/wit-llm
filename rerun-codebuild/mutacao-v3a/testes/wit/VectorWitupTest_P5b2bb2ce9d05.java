/* @test    @summary Tests for java.util.Vector.listIterator(int index) to verify exception behavior and normal operation    @run main VectorWitupTest_P5b2bb2ce9d05 */

import java.util.Vector;
import java.util.ListIterator;

public class VectorWitupTest_P5b2bb2ce9d05 {

    public static void main(String[] args) {
        testListIteratorWithValidIndex();
        testListIteratorWithNegativeIndex();
        testListIteratorWithIndexGreaterThanSize();
    }

    private static void testListIteratorWithValidIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        int index = 1;
        ListIterator<String> it = null;
        try {
            it = vector.listIterator(index);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid index", e);
        }
        if (it == null) {
            throw new AssertionError("ListIterator should not be null for valid index");
        }
        // Verify that the iterator points to the correct position
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element at index " + index);
        }
        String nextElement = it.next();
        if (!nextElement.equals("b")) {
            throw new AssertionError("Expected 'b', got '" + nextElement + "'");
        }
    }

    private static void testListIteratorWithNegativeIndex() {
        Vector<String> vector = new Vector<>();
        vector.add("x");
        vector.add("y");
        vector.add("z");
        int invalidIndex = -1;
        boolean exceptionThrown = false;
        try {
            vector.listIterator(invalidIndex);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testListIteratorWithIndexGreaterThanSize() {
        Vector<String> vector = new Vector<>();
        vector.add("foo");
        vector.add("bar");
        int invalidIndex = 3; // size is 2
        boolean exceptionThrown = false;
        try {
            vector.listIterator(invalidIndex);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than size");
        }
    }
}
