/*
 * @test
 * @summary Test StringCharacterIterator(String text, int begin, int end, int pos) constructor
 * @run main StringCharacterIteratorWitupTest
 */

public class StringCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNullText();
        testInvalidBeginEnd();
        testInvalidPos();
    }

    private static void testValidConstruction() {
        String s = "Hello, world!";
        // Normal case: full string
        java.text.StringCharacterIterator it = new java.text.StringCharacterIterator(s, 0, s.length(), 0);
        if (it.current() != 'H') throw new RuntimeException("Expected 'H' at current position");

        // Substring range
        it = new java.text.StringCharacterIterator(s, 7, 12, 7);
        if (it.current() != 'w') throw new RuntimeException("Expected 'w' at current position");

        // Position at end
        it = new java.text.StringCharacterIterator(s, 0, s.length(), s.length());
        if (it.current() != java.text.CharacterIterator.DONE) throw new RuntimeException("Expected DONE at end position");
    }

    private static void testNullText() {
        try {
            new java.text.StringCharacterIterator(null, 0, 0, 0);
            throw new RuntimeException("Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testInvalidBeginEnd() {
        String s = "abc";
        // begin < 0
        try {
            new java.text.StringCharacterIterator(s, -1, 2, 0);
            throw new RuntimeException("Expected IllegalArgumentException for begin < 0");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // begin > end
        try {
            new java.text.StringCharacterIterator(s, 2, 1, 1);
            throw new RuntimeException("Expected IllegalArgumentException for begin > end");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // end > text.length()
        try {
            new java.text.StringCharacterIterator(s, 0, s.length() + 1, 0);
            throw new RuntimeException("Expected IllegalArgumentException for end > text.length()");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidPos() {
        String s = "abc";
        // pos < begin
        try {
            new java.text.StringCharacterIterator(s, 0, 3, -1);
            throw new RuntimeException("Expected IllegalArgumentException for pos < begin");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // pos > end
        try {
            new java.text.StringCharacterIterator(s, 0, 3, 4);
            throw new RuntimeException("Expected IllegalArgumentException for pos > end");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
