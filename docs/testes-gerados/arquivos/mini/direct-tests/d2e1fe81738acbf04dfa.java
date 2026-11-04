/*
 * @test
 * @summary Test jdk.internal.util.xml.impl.Attrs.isDeclared(String) method
 * @run main AttrsWitupTest
 */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {
    public static void main(String[] args) {
        Attrs attrs = new Attrs();

        // Setup attributes: 2 attributes
        attrs.setLength((char)2);

        // Manually fill mItems array for 2 attributes (each attribute uses 8 slots, but only first 6 used)
        // Attribute 0: qName = "attr0", declared = non-null
        attrs.mItems[1] = "attr0"; // qName at offset +1
        attrs.mItems[5] = "d";     // declared flag non-null at offset +5

        // Attribute 1: qName = "attr1", declared = null
        attrs.mItems[9] = "attr1"; // qName at offset +1 for attribute 1 (index 1 << 3 = 8, +1 = 9)
        attrs.mItems[13] = null;     // declared flag null at offset +5 for attribute 1

        // Test isDeclared returns true for declared attribute
        if (!attrs.isDeclared("attr0")) {
            throw new RuntimeException("Expected isDeclared to return true for declared attribute 'attr0'");
        }

        // Test isDeclared returns false for non-declared attribute
        if (attrs.isDeclared("attr1")) {
            throw new RuntimeException("Expected isDeclared to return false for non-declared attribute 'attr1'");
        }

        // Test isDeclared throws IllegalArgumentException for unknown qName
        boolean threw = false;
        try {
            attrs.isDeclared("unknown");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for unknown qName");
        }
    }
}
