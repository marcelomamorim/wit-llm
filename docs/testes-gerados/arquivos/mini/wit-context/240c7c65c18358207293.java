/*
 * @test
 * @summary Test StringUTF16.codePointCountSB method for valid and invalid inputs
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testValidInput();
        testInvalidInputs();
        System.out.println("StringUTF16WitupTest passed.");
    }

    private static void testValidInput() {
        // Prepare a valid UTF-16 byte array representing "A" (U+0041) and "\uD834\uDD1E" (a surrogate pair)
        // 'A' = 0x0041 -> bytes: 00 41
        // U+1D11E (MUSICAL SYMBOL G CLEF) = high surrogate 0xD834, low surrogate 0xDD1E
        // high surrogate 0xD834 -> bytes: D8 34
        // low surrogate 0xDD1E -> bytes: DD 1E
        byte[] val = new byte[] {
            0x00, 0x41, // 'A'
            (byte)0xD8, 0x34, // high surrogate
            (byte)0xDD, 0x1E  // low surrogate
        };

        // codePointCountSB with full range should count 2 code points
        int count = java.lang.StringUTF16.codePointCountSB(val, 0, val.length >> 1);
        if (count != 2) {
            throw new AssertionError("Expected 2 code points, got " + count);
        }

        // codePointCountSB with subrange covering only 'A'
        count = java.lang.StringUTF16.codePointCountSB(val, 0, 1);
        if (count != 1) {
            throw new AssertionError("Expected 1 code point for subrange, got " + count);
        }
    }

    private static void testInvalidInputs() {
        byte[] val = new byte[] {0x00, 0x41}; // 'A'

        // According to WIT expath and source, codePointCountSB should throw StringIndexOutOfBoundsException
        // if beginIndex > endIndex, or endIndex > length(val), or beginIndex < 0, and beginIndex < endIndex

        // Case: beginIndex > endIndex
        try {
            java.lang.StringUTF16.codePointCountSB(val, 2, 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for beginIndex > endIndex");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }

        // Case: endIndex > length(val) (length(val) = val.length >> 1 = 1)
        try {
            java.lang.StringUTF16.codePointCountSB(val, 0, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for endIndex > length(val)");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }

        // Case: beginIndex < 0
        try {
            java.lang.StringUTF16.codePointCountSB(val, -1, 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for beginIndex < 0");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }

        // Case: beginIndex == endIndex (should not throw, returns 0)
        int count = java.lang.StringUTF16.codePointCountSB(val, 1, 1);
        if (count != 0) {
            throw new AssertionError("Expected 0 code points when beginIndex == endIndex, got " + count);
        }
    }
}
