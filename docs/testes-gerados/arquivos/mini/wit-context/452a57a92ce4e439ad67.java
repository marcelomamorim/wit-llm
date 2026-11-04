/*
 * @test
 * @summary Test Vector.removeElementAt(int) for bounds checking and correct removal
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRemoveElementAtValidIndex();
        testRemoveElementAtIndexEqualElementCount();
        testRemoveElementAtNegativeIndex();
    }

    private static void testRemoveElementAtValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        int initialSize = v.size();
        v.removeElementAt(1); // remove "b"
        if (v.size() != initialSize - 1) {
            throw new RuntimeException("Size did not decrease after removeElementAt");
        }
        if (v.contains("b")) {
            throw new RuntimeException("Element at index 1 was not removed");
        }
        if (!"a".equals(v.get(0)) || !"c".equals(v.get(1))) {
            throw new RuntimeException("Elements shifted incorrectly after removal");
        }
    }

    private static void testRemoveElementAtIndexEqualElementCount() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.add("y");
        int size = v.size();
        try {
            v.removeElementAt(size); // index == elementCount, should throw
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index == elementCount");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRemoveElementAtNegativeIndex() {
        Vector<String> v = new Vector<>();
        v.add("x");
        try {
            v.removeElementAt(-1); // negative index, should throw
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
