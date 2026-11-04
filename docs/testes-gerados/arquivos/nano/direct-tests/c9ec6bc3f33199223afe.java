/* @test    @summary Test String(byte[], int, int, int) constructor for various inputs    @run main StringWitupTest */

import java.lang.String;
import java.util.Arrays;

public class StringWitupTest {

    public static void main(String[] args) {
        testEmptyAsciiArray();
        testLatin1Encoding();
        testUtf16Encoding();
        testNullByteArray();
        testZeroCount();
        testNegativeOffset();
        testCountExceedsArray();
    }

    static void checkBoundsOffCount(int offset, int count, int length) {
        if (offset < 0 || count < 0 || offset > length - count) {
            throw new IndexOutOfBoundsException();
        }
    }

    static void testEmptyAsciiArray() {
        byte[] ascii = new byte[0];
        String s = new String(ascii, 0, 0, 0);
        assert s.equals("") : "Empty array should produce empty string";
    }

    static void testLatin1Encoding() {
        byte[] ascii = {65, 66, 67}; // 'A', 'B', 'C'
        String s = new String(ascii, 0, 0, 3);
        assert s.equals("ABC") : "Expected 'ABC', got '" + s + "'";
    }

    static void testUtf16Encoding() {
        byte[] ascii = {0x41, 0x42, 0x43}; // 'A', 'B', 'C'
        int hibyte = 1; // non-zero to trigger UTF16 path
        String s = new String(ascii, hibyte, 0, 3);
        // The string should contain characters with high byte 1 and low byte from ascii
        // Since the actual characters depend on the byte combination, check length and content
        assert s.length() == 3 : "Expected length 3, got " + s.length();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int expectedCharCode = (hibyte << 8) | (ascii[i] & 0xff);
            assert c == expectedCharCode : "Expected char code " + expectedCharCode + ", got " + (int)c;
        }
    }

    static void testNullByteArray() {
        try {
            new String((byte[])null, 0, 0, 0);
            assert false : "Null array should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testZeroCount() {
        byte[] ascii = {65, 66, 67};
        String s = new String(ascii, 0, 1, 0);
        assert s.equals("") : "Zero count should produce empty string";
    }

    static void testNegativeOffset() {
        byte[] ascii = {65, 66, 67};
        try {
            new String(ascii, -1, 0, 0);
            assert false : "Negative offset should throw IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCountExceedsArray() {
        byte[] ascii = {65, 66, 67};
        try {
            new String(ascii, 0, 0, 4);
            assert false : "Count exceeding array length should throw IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
