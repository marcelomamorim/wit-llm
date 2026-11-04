/*
 * @test
 * @summary Test StringCharacterIterator(String text, int begin, int end, int pos) constructor for exception and normal cases
 * @run main StringCharacterIteratorWitupTest
 */

public class StringCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testNullTextThrowsNPE();
        testInvalidBeginEndThrowsIAE();
        testInvalidPosThrowsIAE();
        testValidConstructionAndState();
    }

    private static void testNullTextThrowsNPE() {
        try {
            new java.text.StringCharacterIterator(null, 0, 0, 0);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testInvalidBeginEndThrowsIAE() {
        String s = "abc";
        // begin < 0
        try {
            new java.text.StringCharacterIterator(s, -1, 2, 0);
            throw new RuntimeException("Expected IllegalArgumentException for begin < 0 not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        // begin > end
        try {
            new java.text.StringCharacterIterator(s, 2, 1, 1);
            throw new RuntimeException("Expected IllegalArgumentException for begin > end not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        // end > text.length()
        try {
            new java.text.StringCharacterIterator(s, 0, s.length() + 1, 0);
            throw new RuntimeException("Expected IllegalArgumentException for end > text.length() not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testInvalidPosThrowsIAE() {
        String s = "abc";
        // pos < begin
        try {
            new java.text.StringCharacterIterator(s, 0, s.length(), -1);
            throw new RuntimeException("Expected IllegalArgumentException for pos < begin not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        // pos > end
        try {
            new java.text.StringCharacterIterator(s, 0, s.length(), s.length() + 1);
            throw new RuntimeException("Expected IllegalArgumentException for pos > end not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testValidConstructionAndState() {
        String s = "hello world";
        int begin = 2;
        int end = 7;
        int pos = 4;
        java.text.StringCharacterIterator it = new java.text.StringCharacterIterator(s, begin, end, pos);

        // Check initial position
        if (it.current() != s.charAt(pos)) {
            throw new RuntimeException("Current character mismatch at pos");
        }

        // Check begin and end boundaries indirectly by moving iterator
        // Move to begin
        it.first();
        if (it.current() != s.charAt(begin)) {
            throw new RuntimeException("Current character mismatch at begin");
        }

        // Move to end (should be DONE)
        it.last();
        if (it.current() != s.charAt(end - 1)) {
            throw new RuntimeException("Current character mismatch at last");
        }

        // Move past end
        it.setIndex(end);
        if (it.current() != java.text.CharacterIterator.DONE) {
            throw new RuntimeException("Expected DONE at end index");
        }
    }
}
