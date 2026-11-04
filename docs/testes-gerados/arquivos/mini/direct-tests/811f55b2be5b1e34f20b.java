/*
 * @test
 * @summary Test ObjectOutputStream.writeChars(String) with various string inputs
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testWriteCharsEmptyString();
        testWriteCharsSimpleString();
        testWriteCharsLongString();
    }

    private static void testWriteCharsEmptyString() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeChars("");
        oos.flush();
        byte[] result = baos.toByteArray();
        // Empty string should produce no char data
        if (result.length != 0) {
            throw new AssertionError("Expected empty output for empty string, got length " + result.length);
        }
        oos.close();
    }

    private static void testWriteCharsSimpleString() throws IOException {
        String testStr = "ABC";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeChars(testStr);
        oos.flush();
        byte[] result = baos.toByteArray();
        // Each char is 2 bytes, so length should be 6
        if (result.length != testStr.length() * 2) {
            throw new AssertionError("Expected " + (testStr.length() * 2) + " bytes, got " + result.length);
        }
        // Check that chars are correctly encoded as big-endian UTF-16
        for (int i = 0; i < testStr.length(); i++) {
            char c = testStr.charAt(i);
            int hi = (result[2*i] & 0xFF);
            int lo = (result[2*i + 1] & 0xFF);
            int val = (hi << 8) + lo;
            if (val != c) {
                throw new AssertionError("Char at index " + i + " expected " + (int)c + " but got " + val);
            }
        }
        oos.close();
    }

    private static void testWriteCharsLongString() throws IOException {
        // Create a string longer than CHAR_BUF_SIZE (1024) to test chunking
        int length = 1500;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char)('A' + (i % 26)));
        }
        String longStr = sb.toString();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeChars(longStr);
        oos.flush();
        byte[] result = baos.toByteArray();

        if (result.length != length * 2) {
            throw new AssertionError("Expected " + (length * 2) + " bytes, got " + result.length);
        }

        // Spot check some chars
        for (int i = 0; i < length; i += 500) {
            char expected = (char)('A' + (i % 26));
            int hi = (result[2*i] & 0xFF);
            int lo = (result[2*i + 1] & 0xFF);
            int val = (hi << 8) + lo;
            if (val != expected) {
                throw new AssertionError("Char at index " + i + " expected " + (int)expected + " but got " + val);
            }
        }
        oos.close();
    }
}
