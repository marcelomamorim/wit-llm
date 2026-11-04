/* @test    @summary Test offsetByCodePoints method of StringBuffer for correct behavior and synchronization.    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testOffsetByCodePointsBasic();
        testOffsetByCodePointsEdgeCases();
    }

    private static void testOffsetByCodePointsBasic() {
        StringBuffer sb = new StringBuffer("abc");
        int result = sb.offsetByCodePoints(0, 1);
        if (result != 1) {
            throw new AssertionError("Expected 1, but got " + result);
        }
        result = sb.offsetByCodePoints(1, 1);
        if (result != 2) {
            throw new AssertionError("Expected 2, but got " + result);
        }
        result = sb.offsetByCodePoints(2, 1);
        if (result != 3) {
            throw new AssertionError("Expected 3, but got " + result);
        }
    }

    private static void testOffsetByCodePointsEdgeCases() {
        StringBuffer sb = new StringBuffer("\uD83D\uDE00\uD83D\uDE02"); // Emoji characters
        int length = sb.length();
        int codePointCount = sb.codePointCount(0, length);
        int offset = sb.offsetByCodePoints(0, codePointCount);
        if (offset != length) {
            throw new AssertionError("Offset by code points to end should equal length. Got: " + offset);
        }
        try {
            sb.offsetByCodePoints(-1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.offsetByCodePoints(0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative codePointOffset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.offsetByCodePoints(length, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
