/*
 * @test
 * @summary Test constructor AttributedString(String text) for null and normal input
 * @run main AttributedStringWitupTest
 */

public class AttributedStringWitupTest {
    public static void main(String[] args) {
        testNullInput();
        testNormalInput();
    }

    private static void testNullInput() {
        try {
            new java.text.AttributedString(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNormalInput() {
        String sample = "sample text";
        java.text.AttributedString as = new java.text.AttributedString(sample);
        if (as == null) {
            throw new RuntimeException("AttributedString instance is null");
        }
        // The text field is private, so we test by getting an iterator and checking characters
        java.text.AttributedCharacterIterator it = as.getIterator();
        StringBuilder sb = new StringBuilder();
        for (char c = it.first(); c != java.text.AttributedCharacterIterator.DONE; c = it.next()) {
            sb.append(c);
        }
        if (!sample.equals(sb.toString())) {
            throw new RuntimeException("Text content mismatch: expected '" + sample + "' but got '" + sb.toString() + "'");
        }
    }
}
