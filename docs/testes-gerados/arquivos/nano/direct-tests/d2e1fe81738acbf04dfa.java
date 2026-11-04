/* @test    @summary Test for Attrs.isDeclared method behavior.    @run main AttrsWitupTest */

import jdk.internal.util.xml.impl.Attrs;

public class AttrsWitupTest {

    public static void main(String[] args) {
        testIsDeclaredWithDeclaredAttribute();
        testIsDeclaredWithUndeclaredAttribute();
        testIsDeclaredWithNullItem();
    }

    private static void testIsDeclaredWithDeclaredAttribute() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        // Setup internal mItems array to simulate declared attribute
        // mItems: ["namespace", "qName", "localName", "value", "type", "d"]
        // index 0: namespace, index 1: qName, index 2: localName, index 3: value, index 4: type, index 5: declared
        attrs.mItems = new String[] {"ns", "qName", "local", "value", "type", "d"};
        // Set mLength to 1
        attrs.setLength((char) 1);
        // Call isDeclared with qName
        boolean result = attrs.isDeclared("qName");
        if (!result) {
            throw new AssertionError("Expected isDeclared to return true for declared attribute.");
        }
    }

    private static void testIsDeclaredWithUndeclaredAttribute() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        // Setup internal mItems array to simulate undeclared attribute
        attrs.mItems = new String[] {"ns", "qName", "local", "value", "type", null};
        // Set mLength to 1
        attrs.setLength((char) 1);
        // Call isDeclared with qName
        boolean result = attrs.isDeclared("qName");
        if (result) {
            throw new AssertionError("Expected isDeclared to return false for undeclared attribute.");
        }
    }

    private static void testIsDeclaredWithNullItem() {
        Attrs attrs = new Attrs();
        attrs.setLength((char) 1);
        // Setup internal mItems array with null at index 5
        attrs.mItems = new String[] {"ns", "qName", "local", "value", "type", null};
        // Set mLength to 1
        attrs.setLength((char) 1);
        // Call isDeclared with qName
        boolean result = attrs.isDeclared("qName");
        if (result) {
            throw new AssertionError("Expected isDeclared to return false when item is null.");
        }
    }
}
