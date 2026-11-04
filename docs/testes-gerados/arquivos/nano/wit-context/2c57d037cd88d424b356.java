/* @test    @summary Test for AttributedString constructor with null argument to verify NullPointerException is thrown.    @run main AttributedStringWitupTest */

import java.text.AttributedString;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
    }

    public static void testConstructorWithNull() {
        boolean exceptionThrown = false;
        try {
            new AttributedString((String) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException was not thrown.");
        }
    }
}
