/*
 * @test
 * @summary Test java.util.Vector.setElementAt(E obj, int index) method
 * @run main VectorWitupTest_P6315d36c6479
 */

import java.util.Vector;

public class VectorWitupTest_P6315d36c6479 {
    public static void main(String[] args) {
        testSetElementAtValidIndex();
        testSetElementAtInvalidIndex();
    }

    private static void testSetElementAtValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("zero");
        v.add("one");
        v.add("two");

        // Replace element at index 1
        v.setElementAt("ONE", 1);

        if (!"ONE".equals(v.get(1))) {
            throw new RuntimeException("Failed to set element at valid index");
        }

        // Replace element at index 0 with null
        v.setElementAt(null, 0);
        if (v.get(0) != null) {
            throw new RuntimeException("Failed to set null element at valid index");
        }
    }

    private static void testSetElementAtInvalidIndex() {
        Vector<String> v = new Vector<>();
        v.add("zero");

        try {
            v.setElementAt("fail", 1); // index == elementCount, should throw
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("1 >= 1")) {
                throw new RuntimeException("Exception message incorrect: " + msg);
            }
        }

        try {
            v.setElementAt("fail", 100); // index > elementCount, should throw
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("100 >= 1")) {
                throw new RuntimeException("Exception message incorrect: " + msg);
            }
        }

        try {
            v.setElementAt("fail", -1); // negative index, should throw
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // message may not contain negative index, just accept exception
        }
    }
}
