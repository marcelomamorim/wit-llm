/*
 * @test
 * @summary Test jdk.internal.util.xml.impl.Attrs.isDeclared(int) method
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {
    public static void main(String[] args) {
        Attrs attrs = new Attrs();

        // Setup with 2 attributes
        attrs.setLength((char)2);

        // Initially all mItems are null, so isDeclared should return false
        for (int i = 0; i < 2; i++) {
            if (attrs.isDeclared(i)) {
                throw new RuntimeException("Expected isDeclared to be false for index " + i);
            }
        }

        // Set declared flag (mItems[(index << 3) + 5]) to non-null for index 0
        // mItems length is length * 8, so index 0 offset is 5
        attrs.mItems[5] = "d";

        // Now isDeclared(0) should return true
        if (!attrs.isDeclared(0)) {
            throw new RuntimeException("Expected isDeclared to be true for index 0 after setting declared flag");
        }

        // isDeclared(1) should still be false
        if (attrs.isDeclared(1)) {
            throw new RuntimeException("Expected isDeclared to be false for index 1");
        }

        // Test exception for negative index
        try {
            attrs.isDeclared(-1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }

        // Test exception for index equal to length
        try {
            attrs.isDeclared(2);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 2");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }

        // Test exception for index greater than length
        try {
            attrs.isDeclared(3);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index 3");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
