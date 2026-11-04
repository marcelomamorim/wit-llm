/* @test    @summary Test for StringUTF16.codePointAtSB method to verify correct code point retrieval and exception handling.    @run main StringUTF16WitupTest */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testValidCodePoint();
        testHighSurrogate();
        testLowSurrogate();
        testOutOfBounds();
    }

    static void testValidCodePoint() {
        byte[] data = {0x00, 0x41, 0xD8, 0x34, 0xDC, 0x1E}; // 'A', surrogate pair for U+1F11E
        int index = 0;
        int end = data.length;
        int codePoint = StringUTF16.codePointAtSB(data, index, end);
        assert codePoint == 0x0041 : "Expected U+0041, got " + Integer.toHexString(codePoint);
    }

    static void testHighSurrogate() {
        byte[] data = {0xD8, 0x34, 0x00, 0x41}; // high surrogate only
        int index = 0;
        int end = data.length;
        int codePoint = StringUTF16.codePointAtSB(data, index, end);
        assert codePoint == 0xD834 : "Expected high surrogate U+D834, got " + Integer.toHexString(codePoint);
    }

    static void testLowSurrogate() {
        byte[] data = {0x00, 0x41, 0xDC, 0x1E}; // low surrogate only
        int index = 2;
        int end = data.length;
        int codePoint = StringUTF16.codePointAtSB(data, index, end);
        assert codePoint == 0xDC1E : "Expected low surrogate U+DC1E, got " + Integer.toHexString(codePoint);
    }

    static void testOutOfBounds() {
        byte[] data = {0x00, 0x41};
        int index = 2; // out of bounds
        int end = data.length;
        try {
            StringUTF16.codePointAtSB(data, index, end);
            assert false : "Expected ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
