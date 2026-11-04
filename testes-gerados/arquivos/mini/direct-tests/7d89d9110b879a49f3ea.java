/*
 * @test
 * @summary Test StringUTF16.stripTrailing(byte[]) behavior
 * @run main java.lang.StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testEmptyArray();
        testAllWhitespace();
        testNoTrailingWhitespace();
        testTrailingWhitespace();
    }

    private static void testEmptyArray() {
        byte[] empty = new byte[0];
        String result = java.lang.StringUTF16.stripTrailing(empty);
        if (result == null) {
            throw new Error("Expected empty string, got null");
        }
        if (!"".equals(result)) {
            throw new Error("Expected empty string, got: '" + result + "'");
        }
    }

    private static void testAllWhitespace() {
        // whitespace chars: space (0x0020), tab (0x0009), newline (0x000A)
        // UTF16 encoding: each char 2 bytes, high byte then low byte
        // space: 0x00 0x20
        // tab: 0x00 0x09
        // newline: 0x00 0x0A
        byte[] ws = new byte[] {
            0x00, 0x20, // space
            0x00, 0x09, // tab
            0x00, 0x0A  // newline
        };
        String result = java.lang.StringUTF16.stripTrailing(ws);
        if (result == null) {
            throw new Error("Expected empty string for all whitespace, got null");
        }
        if (!"".equals(result)) {
            throw new Error("Expected empty string for all whitespace, got: '" + result + "'");
        }
    }

    private static void testNoTrailingWhitespace() {
        // chars: 'A' (0x0041), 'B' (0x0042), 'C' (0x0043)
        byte[] abc = new byte[] {
            0x00, 0x41, // 'A'
            0x00, 0x42, // 'B'
            0x00, 0x43  // 'C'
        };
        String result = java.lang.StringUTF16.stripTrailing(abc);
        if (result != null) {
            throw new Error("Expected null for no trailing whitespace, got: '" + result + "'");
        }
    }

    private static void testTrailingWhitespace() {
        // chars: 'A' (0x0041), ' ' (0x0020), ' ' (0x0020)
        byte[] aSpaces = new byte[] {
            0x00, 0x41, // 'A'
            0x00, 0x20, // space
            0x00, 0x20  // space
        };
        String result = java.lang.StringUTF16.stripTrailing(aSpaces);
        if (result == null) {
            throw new Error("Expected non-null string after stripping trailing whitespace, got null");
        }
        if (!"A".equals(result)) {
            throw new Error("Expected 'A' after stripping trailing whitespace, got: '" + result + "'");
        }
    }
}
