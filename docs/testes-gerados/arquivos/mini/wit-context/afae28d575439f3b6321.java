/*
 * @test
 * @summary Test String.offsetByCodePoints(int, int) for boundary and exception cases
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidOffsets();
        testIndexOutOfBounds();
        System.out.println("StringWitupTest passed.");
    }

    private static void testValidOffsets() {
        String s = "abc\uD834\uDD1Edef"; // includes a surrogate pair (𝄞)
        int len = s.length();

        // index at 0, offset 0 should return 0
        int result = s.offsetByCodePoints(0, 0);
        if (result != 0) {
            throw new AssertionError("Expected 0 but got " + result);
        }

        // index at 0, offset 1 should move to next code point (1 or 2 depending on surrogate)
        int expected = Character.offsetByCodePoints(s, 0, 1);
        int actual = s.offsetByCodePoints(0, 1);
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }

        // index at length, offset 0 should return length
        result = s.offsetByCodePoints(len, 0);
        if (result != len) {
            throw new AssertionError("Expected " + len + " but got " + result);
        }

        // index at length, offset -1 should move back one code point
        expected = Character.offsetByCodePoints(s, len, -1);
        actual = s.offsetByCodePoints(len, -1);
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    private static void testIndexOutOfBounds() {
        String s = "abc";
        int len = s.length();

        // index < 0 should throw IndexOutOfBoundsException
        try {
            s.offsetByCodePoints(-1, 0);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        // index > length should throw IndexOutOfBoundsException
        try {
            s.offsetByCodePoints(len + 1, 0);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
