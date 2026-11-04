/* @test    @summary Test String.getBytes() method for correct byte array output.    @run main StringWitupTest */

import java.lang.String;
import java.util.Arrays;

public class StringWitupTest {

    public static void main(String[] args) {
        testGetBytesEmptyString();
        testGetBytesSimpleString();
        testGetBytesUnicodeString();
    }

    static void testGetBytesEmptyString() {
        String s = "";
        byte[] result = s.getBytes();
        byte[] expected = new byte[0];
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed: getBytes() on empty string");
        }
    }

    static void testGetBytesSimpleString() {
        String s = "abc";
        byte[] result = s.getBytes();
        byte[] expected = {97, 98, 99}; // ASCII codes for 'a', 'b', 'c'
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed: getBytes() on 'abc'");
        }
    }

    static void testGetBytesUnicodeString() {
        String s = "é"; // Unicode character U+00E9
        byte[] result = s.getBytes();
        // The actual byte array depends on the default charset, typically UTF-8
        // For JDK 11+, getBytes() uses platform default charset, which is often UTF-8
        byte[] expected = { (byte)0xC3, (byte)0xA9 }; // UTF-8 encoding of 'é'
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Failed: getBytes() on 'é'");
        }
    }
}
