/*
 * @test
 * @summary Test jdk.internal.util.xml.impl.Attrs.isSpecified(int) for valid and invalid indices
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {
    public static void main(String[] args) {
        Attrs attrs = new Attrs();

        // Setup attributes array with length 2
        attrs.setLength((char)2);

        // Prepare mItems array with 2 attributes (each attribute uses 8 slots, but only first 6 used)
        // For index 0: mItems[0*8 + 5] = "d" (declared)
        // For index 1: mItems[1*8 + 5] = null
        // We must fill the array to avoid NullPointerException
        String[] items = new String[2 << 3];
        items[5] = "d"; // index 0 declared
        items[13] = null; // index 1 no declared flag
        attrs.mItems = items;

        // Test valid index 0, expected true because charAt(0) == 'd'
        if (!attrs.isSpecified(0)) {
            throw new AssertionError("Expected isSpecified(0) to be true");
        }

        // Test valid index 1, expected true because str == null returns true
        if (!attrs.isSpecified(1)) {
            throw new AssertionError("Expected isSpecified(1) to be true when str is null");
        }

        // Test invalid index -1, expect ArrayIndexOutOfBoundsException
        try {
            attrs.isSpecified(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }

        // Test invalid index equal to length (2), expect ArrayIndexOutOfBoundsException
        try {
            attrs.isSpecified(2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 2");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }

        // Test invalid index greater than length (3), expect ArrayIndexOutOfBoundsException
        try {
            attrs.isSpecified(3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 3");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
