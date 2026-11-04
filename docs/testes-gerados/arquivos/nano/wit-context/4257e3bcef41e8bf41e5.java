/* @test    @summary Test addAttribute method for AttributedString to verify exception paths and normal behavior    @run main AttributedStringWitupTest */

import java.text.AttributedString;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString.Attribute;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testAddAttributeNullAttribute();
        testAddAttributeEmptyText();
        testAddAttributeNormal();
    }

    private static void testAddAttributeNullAttribute() {
        AttributedString as = new AttributedString("Sample text");
        try {
            as.addAttribute(null, "value");
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testAddAttributeEmptyText() {
        // Create an AttributedString with empty text
        AttributedString as = new AttributedString("");
        Attribute attr = new Attribute() {};
        try {
            as.addAttribute(attr, "value");
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testAddAttributeNormal() {
        AttributedString as = new AttributedString("Sample text");
        Attribute attr = new Attribute() {};
        try {
            as.addAttribute(attr, "value");
            System.out.println("PASS: addAttribute executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
