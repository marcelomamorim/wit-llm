/*
 * @test
 * @summary Test jdk.internal.util.xml.impl.Attrs.isSpecified(int) method
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {
    public static void main(String[] args) {
        Attrs attrs = new Attrs();

        // Setup 2 attributes
        attrs.setLength((char)2);

        // Prepare mItems array with 2 attributes (each attribute uses 8 slots)
        // We only need to set the slot at (index << 3) + 5 for isSpecified
        // index 0: mItems[5] = "d" (declared)
        // index 1: mItems[13] = "D" (default)
        // index 2: not set, out of range

        // Fill mItems with enough size
        String[] items = new String[2 << 3];
        items[5] = "d";  // index 0 specified
        items[13] = "D"; // index 1 default
        attrs.mItems = items;

        // Test index 0: str.charAt(0) == 'd' -> true
        if (!attrs.isSpecified(0)) {
            throw new RuntimeException("Test failed: index 0 should be specified (true)");
        }

        // Test index 1: str.charAt(0) != 'd' -> false
        if (attrs.isSpecified(1)) {
            throw new RuntimeException("Test failed: index 1 should NOT be specified (false)");
        }

        // Test index 0 with null string (simulate null at slot 5)
        items[5] = null;
        if (!attrs.isSpecified(0)) {
            throw new RuntimeException("Test failed: index 0 with null string should return true");
        }

        // Test index out of bounds: negative index
        try {
            attrs.isSpecified(-1);
            throw new RuntimeException("Test failed: negative index should throw ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }

        // Test index out of bounds: index >= mLength
        try {
            attrs.isSpecified(2);
            throw new RuntimeException("Test failed: index >= mLength should throw ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
