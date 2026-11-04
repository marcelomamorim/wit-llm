/*
 * @test
 * @summary Verifies DerInputStream.getUTF8String for ASCII, empty, non-ASCII, offset-based and wrong-tag inputs
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import java.io.IOException;
import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testAsciiString();
        testEmptyString();
        testNonAsciiString();
        testOffsetAndLength();
        testWrongTagRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAsciiString() throws Exception {
        byte[] data = {(byte) 0x0C, (byte) 0x03, (byte) 'a', (byte) 'b', (byte) 'c'};
        String s = new DerInputStream(data).getUTF8String();
        check("abc".equals(s), "Expected 'abc' but got: " + s);
    }

    static void testEmptyString() throws Exception {
        byte[] data = {(byte) 0x0C, (byte) 0x00};
        String s = new DerInputStream(data).getUTF8String();
        check(s != null && s.isEmpty(), "Expected empty string but got: " + s);
    }

    static void testNonAsciiString() throws Exception {
        byte[] data = {(byte) 0x0C, (byte) 0x02, (byte) 0xC3, (byte) 0xA9};
        String expected = String.valueOf((char) 0xE9);
        String s = new DerInputStream(data).getUTF8String();
        check(expected.equals(s), "Expected U+00E9 but got: " + s);
    }

    static void testOffsetAndLength() throws Exception {
        byte[] data = {(byte) 0x00, (byte) 0x0C, (byte) 0x02, (byte) 'h', (byte) 'i'};
        String s = new DerInputStream(data, 1, 4, true).getUTF8String();
        check("hi".equals(s), "Expected 'hi' with offset 1 but got: " + s);
    }

    static void testWrongTagRejected() throws Exception {
        byte[] data = {(byte) 0x04, (byte) 0x01, (byte) 'a'};
        boolean rejected = false;
        try {
            new DerInputStream(data).getUTF8String();
        } catch (IOException e) {
            rejected = true;
        }
        check(rejected, "Expected IOException for non-UTF8String tag");
    }
}
