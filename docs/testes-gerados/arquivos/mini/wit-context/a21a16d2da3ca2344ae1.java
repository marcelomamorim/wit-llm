/*
 * @test
 * @summary Test StringUTF16.codePointAtSB method for bounds checking and correct code point retrieval
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testValidCodePointAtSB();
        testIndexOutOfBoundsNegativeIndex();
        testIndexOutOfBoundsIndexEqualsLength();
        System.out.println("StringUTF16WitupTest passed.");
    }

    private static void testValidCodePointAtSB() {
        // Prepare a byte array representing UTF-16 chars: 'A' (0x0041), '𝄞' (U+1D11E, surrogate pair)
        // 'A' = 0x0041 -> bytes: 00 41
        // '𝄞' = high surrogate 0xD834, low surrogate 0xDD1E
        byte[] val = new byte[6];
        // 'A'
        val[0] = 0x00;
        val[1] = 0x41;
        // high surrogate 0xD8 0x34
        val[2] = (byte)0xD8;
        val[3] = 0x34;
        // low surrogate 0xDD 0x1E
        val[4] = (byte)0xDD;
        val[5] = 0x1E;

        // codePointAtSB should return 'A' code point at index 0
        int cp0 = java.lang.StringUTF16.codePointAtSB(val, 0, val.length);
        if (cp0 != 0x0041) {
            throw new AssertionError("Expected code point 0x0041 at index 0, got " + Integer.toHexString(cp0));
        }

        // codePointAtSB should return code point U+1D11E at index 2 (high surrogate)
        int cp1 = java.lang.StringUTF16.codePointAtSB(val, 2, val.length);
        if (cp1 != 0x1D11E) {
            throw new AssertionError("Expected code point 0x1D11E at index 2, got " + Integer.toHexString(cp1));
        }
    }

    private static void testIndexOutOfBoundsNegativeIndex() {
        byte[] val = new byte[2];
        val[0] = 0x00;
        val[1] = 0x41;
        try {
            java.lang.StringUTF16.codePointAtSB(val, -1, val.length);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testIndexOutOfBoundsIndexEqualsLength() {
        byte[] val = new byte[2];
        val[0] = 0x00;
        val[1] = 0x41;
        int index = java.lang.StringUTF16.length(val); // length returns val.length >> 1 == 1
        try {
            java.lang.StringUTF16.codePointAtSB(val, index, val.length);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index == length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
