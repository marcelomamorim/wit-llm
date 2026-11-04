/*
 * @test
 * @summary Test jdk.internal.util.xml.impl.Attrs.isDeclared(String) behavior
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        Attrs attrs = new Attrs();

        // Test 1: isDeclared throws IllegalArgumentException if no attributes (mLength == 0)
        attrs.setLength((char)0);
        try {
            attrs.isDeclared("any");
            throw new RuntimeException("Expected IllegalArgumentException for empty attributes");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test 2: isDeclared throws IllegalArgumentException if attribute not found (idx < 0)
        attrs.setLength((char)1);
        // Setup mItems so that qName does not match
        // mItems length must be at least 8 (1 << 3)
        attrs.mItems[1] = "notMatchingQName";
        try {
            attrs.isDeclared("someQName");
            throw new RuntimeException("Expected IllegalArgumentException for qName not found");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test 3: isDeclared returns false if attribute found but declared flag is null
        attrs.mItems[1] = "matchQName";
        // local name and others can be null or empty, only declared flag matters
        attrs.mItems[5] = null; // declared flag
        try {
            boolean declared = attrs.isDeclared("matchQName");
            if (declared) {
                throw new RuntimeException("Expected false when declared flag is null");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Did not expect exception when qName matches and idx >= 0", e);
        }

        // Test 4: isDeclared returns true if attribute found and declared flag is non-null
        attrs.mItems[5] = "d"; // declared flag non-null
        try {
            boolean declared = attrs.isDeclared("matchQName");
            if (!declared) {
                throw new RuntimeException("Expected true when declared flag is non-null");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Did not expect exception when qName matches and idx >= 0", e);
        }

        System.out.println("Attrs.isDeclared tests passed");
    }
}
