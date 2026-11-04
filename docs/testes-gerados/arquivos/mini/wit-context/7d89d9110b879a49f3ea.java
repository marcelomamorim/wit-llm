/*
 * @test
 * @summary Test StringUTF16.stripTrailing(byte[]) for normal and edge cases
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testStripTrailingEmptyArray();
        testStripTrailingAllWhitespace();
        testStripTrailingNoTrailingWhitespace();
        testStripTrailingTrailingWhitespace();
    }

    private static void testStripTrailingEmptyArray() {
        byte[] empty = new byte[0];
        String result = java.lang.StringUTF16.stripTrailing(empty);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string for empty input array");
        }
    }

    private static void testStripTrailingAllWhitespace() {
        // Construct a byte array representing only whitespace chars (e.g. space ' ' = 0x0020)
        // UTF16 encoding: each char 2 bytes, high byte 0x00, low byte 0x20
        byte[] whitespace = new byte[] {0x00, 0x20, 0x00, 0x20, 0x00, 0x20};
        String result = java.lang.StringUTF16.stripTrailing(whitespace);
        // lastIndexOfNonWhitespace returns 0 if no non-whitespace found, so expect empty string
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string for all whitespace input");
        }
    }

    private static void testStripTrailingNoTrailingWhitespace() {
        // Construct a byte array representing "abc" (no trailing whitespace)
        // 'a' = 0x0061, 'b' = 0x0062, 'c' = 0x0063
        byte[] abc = new byte[] {0x00, 0x61, 0x00, 0x62, 0x00, 0x63};
        String result = java.lang.StringUTF16.stripTrailing(abc);
        // Since right == length, stripTrailing returns null
        if (result != null) {
            throw new AssertionError("Expected null when no trailing whitespace present");
        }
    }

    private static void testStripTrailingTrailingWhitespace() {
        // Construct a byte array representing "abc " (with trailing space)
        // 'a' = 0x0061, 'b' = 0x0062, 'c' = 0x0063, ' ' = 0x0020
        byte[] abcSpace = new byte[] {0x00, 0x61, 0x00, 0x62, 0x00, 0x63, 0x00, 0x20};
        String result = java.lang.StringUTF16.stripTrailing(abcSpace);
        // Expect result to be "abc" (trailing space removed)
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected 'abc' after stripping trailing whitespace, got: " + result);
        }
    }
}
