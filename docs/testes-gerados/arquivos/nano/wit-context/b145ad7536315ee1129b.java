/* @test    @summary Test for AttributedString.setIndex(int) method to verify behavior and exception handling    @run main AttributedStringWitupTest */

import java.text.AttributedString;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testSetIndexValid();
        testSetIndexInvalid();
    }

    private static void testSetIndexValid() {
        // Create an AttributedString with some text
        AttributedString attrStr = new AttributedString("Sample Text");
        try {
            // Valid index within bounds
            char result = attrStr.setIndex(5);
            // Verify that the method returns a character (not null or exception)
            if (result != ' ' && result != 0) {
                System.out.println("testSetIndexValid passed.");
            } else {
                System.out.println("testSetIndexValid failed: unexpected character.");
            }
        } catch (Exception e) {
            System.out.println("testSetIndexValid failed with exception: " + e);
        }
    }

    private static void testSetIndexInvalid() {
        // Create an AttributedString with some text
        AttributedString attrStr = new AttributedString("Sample Text");
        int beginIndex = 0;
        int endIndex = 11; // length of "Sample Text"
        // Use reflection to access beginIndex and endIndex if needed, but here assume accessible
        // For the test, simulate invalid index: less than beginIndex
        try {
            attrStr.setIndex(-1); // invalid, should throw
            System.out.println("testSetIndexInvalid failed: no exception for negative index.");
        } catch (IllegalArgumentException e) {
            System.out.println("testSetIndexInvalid passed for negative index.");
        } catch (Exception e) {
            System.out.println("testSetIndexInvalid failed with unexpected exception: " + e);
        }
        // Use index greater than endIndex
        try {
            attrStr.setIndex(20); // invalid, should throw
            System.out.println("testSetIndexInvalid failed: no exception for index beyond end.");
        } catch (IllegalArgumentException e) {
            System.out.println("testSetIndexInvalid passed for index beyond end.");
        } catch (Exception e) {
            System.out.println("testSetIndexInvalid failed with unexpected exception: " + e);
        }
    }
}
