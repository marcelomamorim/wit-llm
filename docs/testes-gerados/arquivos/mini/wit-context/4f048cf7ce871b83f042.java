/*
 * @test
 * @summary Test jdk.internal.util.xml.impl.Attrs.isDeclared(int) for valid and invalid indices
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {
    public static void main(String[] args) {
        Attrs attrs = new Attrs();

        // Setup length to 2 attributes
        attrs.setLength((char)2);

        // Setup mItems array with 2 attributes (each attribute uses 8 slots, but isDeclared uses offset +5)
        // We only need to set mItems[(index << 3) + 5] to non-null to test isDeclared true
        // and null to test isDeclared false.
        // Since mItems is package-private, we can access it directly.

        // Clear all to null
        for (int i = 0; i < attrs.mItems.length; i++) {
            attrs.mItems[i] = null;
        }

        // For index 0, set declared flag (offset +5) to non-null string
        attrs.mItems[(0 << 3) + 5] = "d";

        // For index 1, leave declared flag null

        // Test isDeclared for valid index 0 (should be true)
        if (!attrs.isDeclared(0)) {
            throw new AssertionError("Expected isDeclared(0) to be true");
        }

        // Test isDeclared for valid index 1 (should be false)
        if (attrs.isDeclared(1)) {
            throw new AssertionError("Expected isDeclared(1) to be false");
        }

        // Test isDeclared for invalid index -1 (should throw ArrayIndexOutOfBoundsException)
        try {
            attrs.isDeclared(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }

        // Test isDeclared for invalid index equal to length (2) (should throw ArrayIndexOutOfBoundsException)
        try {
            attrs.isDeclared(2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 2");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }

        // Test isDeclared for invalid index greater than length (3) (should throw ArrayIndexOutOfBoundsException)
        try {
            attrs.isDeclared(3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 3");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
