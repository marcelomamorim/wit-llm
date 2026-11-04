/* @test    @summary Test isSpecified method of Attrs class    @run main AttrsWitupTest */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        testIsSpecifiedWithDefaultValue();
        testIsSpecifiedWithExplicitD();
        testIsSpecifiedWithExplicitOther();
        testIsSpecifiedWithInvalidIndex();
    }

    static void testIsSpecifiedWithDefaultValue() {
        Attrs attrs = new Attrs();
        attrs.setLength((char)1);
        // Set mItems for index 0: value at position (0<<3)+5 is null, so should return true
        attrs.mItems = new String[8];
        attrs.mItems[0] = ""; // namespace
        attrs.mItems[1] = "qname"; // qname
        attrs.mItems[2] = "local"; // local name
        attrs.mItems[3] = "value"; // value
        attrs.mItems[4] = "type"; // type
        attrs.mItems[5] = null; // declared
        attrs.mItems[6] = null; // default
        boolean result = false;
        try {
            result = new Attrs().isSpecified(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            result = true; // Should not throw
        }
        if (result != true) {
            throw new AssertionError("Expected true for default null declared");
        }
    }

    static void testIsSpecifiedWithExplicitD() {
        Attrs attrs = new Attrs();
        attrs.setLength((char)1);
        attrs.mItems = new String[8];
        attrs.mItems[0] = "";
        attrs.mItems[1] = "qname";
        attrs.mItems[2] = "local";
        attrs.mItems[3] = "value";
        attrs.mItems[4] = "type";
        attrs.mItems[5] = "d"; // declared with 'd'
        attrs.mItems[6] = null;
        boolean result = attrs.isSpecified(0);
        if (result != true) {
            throw new AssertionError("Expected true for declared 'd'");
        }
    }

    static void testIsSpecifiedWithExplicitOther() {
        Attrs attrs = new Attrs();
        attrs.setLength((char)1);
        attrs.mItems = new String[8];
        attrs.mItems[0] = "";
        attrs.mItems[1] = "qname";
        attrs.mItems[2] = "local";
        attrs.mItems[3] = "value";
        attrs.mItems[4] = "type";
        attrs.mItems[5] = "x"; // not 'd'
        attrs.mItems[6] = null;
        boolean result = attrs.isSpecified(0);
        if (result != false) {
            throw new AssertionError("Expected false for non-'d' declared");
        }
    }

    static void testIsSpecifiedWithInvalidIndex() {
        Attrs attrs = new Attrs();
        attrs.setLength((char)1);
        try {
            attrs.isSpecified(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            attrs.isSpecified(1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index >= length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
