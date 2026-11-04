/*
 * @test
 * @summary Verifies that AttributedString(String) rejects null text with NullPointerException and accepts valid strings
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedString;

public class AttributedStringWitupTest {
    public static void main(String[] args) {
        boolean thrown = false;
        try {
            new AttributedString((String) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null text");
        }

        new AttributedString("");
        new AttributedString("abc");
    }
}
