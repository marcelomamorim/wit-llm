/* @test    @summary Test for Attrs.isDeclared method to verify correct behavior and exception handling.    @run main AttrsWitupTest */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        testIsDeclaredValidIndex();
        testIsDeclaredInvalidNegativeIndex();
        testIsDeclaredInvalidOutOfRangeIndex();
        testIsDeclaredNullValue();
    }

    private static void testIsDeclaredValidIndex() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        // Set mItems for index 0 to have a non-null value at position 5 (declared)
        attrs.mItems = new String[] {"", null, null, null, null, "d"};
        boolean result = attrs.isDeclared(0);
        if (!result) {
            throw new AssertionError("Expected true for declared attribute at index 0");
        }
    }

    private static void testIsDeclaredInvalidNegativeIndex() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        try {
            attrs.isDeclared(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testIsDeclaredInvalidOutOfRangeIndex() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        try {
            attrs.isDeclared(1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for out-of-range index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testIsDeclaredNullValue() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        // Set mItems for index 0 with null at position 5
        attrs.mItems = new String[] {"", null, null, null, null, null};
        boolean result = attrs.isDeclared(0);
        if (result) {
            throw new AssertionError("Expected false for null value at index 0");
        }
    }
}
