/*
 * @test
 * @summary Test codePointCountSB method of StringUTF16
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testEmptyArray();
        testSingleChar();
        testSurrogatePair();
        testMultipleCodePoints();
        testBeginEndIndices();
    }

    private static void testEmptyArray() {
        byte[] val = new byte[0];
        int count = java.lang.StringUTF16.codePointCountSB(val, 0, 0);
        if (count != 0) {
            throw new AssertionError("Expected 0 code points for empty array, got " + count);
        }
    }

    private static void testSingleChar() {
        // single char 'A' (U+0041) in UTF-16 big endian
        byte[] val = new byte[] {0, 65};
        int count = java.lang.StringUTF16.codePointCountSB(val, 0, 1);
        if (count != 1) {
            throw new AssertionError("Expected 1 code point for single char, got " + count);
        }
    }

    private static void testSurrogatePair() {
        // surrogate pair for U+1D11E (musical symbol G clef)
        // high surrogate: 0xD834, low surrogate: 0xDD1E
        byte[] val = new byte[] {
            (byte)0xD8, (byte)0x34, // high surrogate
            (byte)0xDD, (byte)0x1E  // low surrogate
        };
        int count = java.lang.StringUTF16.codePointCountSB(val, 0, 2);
        if (count != 1) {
            throw new AssertionError("Expected 1 code point for surrogate pair, got " + count);
        }
    }

    private static void testMultipleCodePoints() {
        // mix of chars: 'A' + surrogate pair U+1D11E + 'B'
        byte[] val = new byte[] {
            0, 65,               // 'A'
            (byte)0xD8, (byte)0x34, // high surrogate
            (byte)0xDD, (byte)0x1E, // low surrogate
            0, 66                // 'B'
        };
        int count = java.lang.StringUTF16.codePointCountSB(val, 0, 4);
        if (count != 3) {
            throw new AssertionError("Expected 3 code points for mixed chars, got " + count);
        }
    }

    private static void testBeginEndIndices() {
        // array with 3 chars: 'A', 'B', 'C'
        byte[] val = new byte[] {0, 65, 0, 66, 0, 67};
        // count code points from index 1 to 3 (should be 'B' and 'C')
        int count = java.lang.StringUTF16.codePointCountSB(val, 1, 3);
        if (count != 2) {
            throw new AssertionError("Expected 2 code points for subrange, got " + count);
        }
    }
}