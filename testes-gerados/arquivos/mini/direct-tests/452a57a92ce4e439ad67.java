/*
 * @test
 * @summary Test java.util.Vector.removeElementAt(int) for correct behavior and exceptions
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRemoveElementAtValidIndex();
        testRemoveElementAtIndexEqualsElementCount();
        testRemoveElementAtNegativeIndex();
    }

    private static void testRemoveElementAtValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("zero");
        v.add("one");
        v.add("two");
        v.add("three");

        // Remove element at index 1 ("one")
        v.removeElementAt(1);

        // After removal, size should be 3
        if (v.size() != 3) {
            throw new RuntimeException("Size after removal expected 3 but was " + v.size());
        }

        // Elements should be ["zero", "two", "three"]
        if (!"zero".equals(v.get(0))) {
            throw new RuntimeException("Element at index 0 expected 'zero' but was '" + v.get(0) + "'");
        }
        if (!"two".equals(v.get(1))) {
            throw new RuntimeException("Element at index 1 expected 'two' but was '" + v.get(1) + "'");
        }
        if (!"three".equals(v.get(2))) {
            throw new RuntimeException("Element at index 2 expected 'three' but was '" + v.get(2) + "'");
        }
    }

    private static void testRemoveElementAtIndexEqualsElementCount() {
        Vector<Integer> v = new Vector<>();
        v.add(10);
        v.add(20);

        try {
            v.removeElementAt(2); // index == elementCount, should throw
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index == elementCount");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("2 >= 2")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
    }

    private static void testRemoveElementAtNegativeIndex() {
        Vector<Integer> v = new Vector<>();
        v.add(1);

        try {
            v.removeElementAt(-1); // negative index, should throw
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.equals("-1")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
    }
}
