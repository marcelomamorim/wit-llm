/*
 * @test
 * @summary Test AttributedString(String) constructor for null and valid input
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedString;

public class AttributedStringWitupTest {
    public static void main(String[] args) {
        testNullInput();
        testValidInput();
        System.out.println("All tests passed.");
    }

    private static void testNullInput() {
        try {
            new AttributedString((String) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidInput() {
        String sample = "sample text";
        AttributedString as = new AttributedString(sample);
        if (as == null) {
            throw new RuntimeException("AttributedString constructor returned null");
        }
    }
}
