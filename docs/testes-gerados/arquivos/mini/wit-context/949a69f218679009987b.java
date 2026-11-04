/*
 * @test
 * @summary Test StringWriter.write(String, int, int) for valid and invalid inputs
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {
    public static void main(String[] args) {
        testValidWrite();
        testWriteWithNegativeOffset();
        testWriteWithNegativeLength();
        testWriteWithOffsetPlusLengthExceedingStringLength();
        testWriteWithNullString();
    }

    private static void testValidWrite() {
        StringWriter sw = new StringWriter();
        String s = "Hello, world!";
        sw.write(s, 7, 5); // writes "world"
        String result = sw.toString();
        if (!"world".equals(result)) {
            throw new RuntimeException("testValidWrite failed: expected 'world' but got '" + result + "'");
        }
    }

    private static void testWriteWithNegativeOffset() {
        StringWriter sw = new StringWriter();
        String s = "Hello";
        try {
            sw.write(s, -1, 2);
            throw new RuntimeException("testWriteWithNegativeOffset failed: expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testWriteWithNegativeLength() {
        StringWriter sw = new StringWriter();
        String s = "Hello";
        try {
            sw.write(s, 1, -2);
            throw new RuntimeException("testWriteWithNegativeLength failed: expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testWriteWithOffsetPlusLengthExceedingStringLength() {
        StringWriter sw = new StringWriter();
        String s = "Hello";
        try {
            sw.write(s, 3, 3); // 3 + 3 = 6 > s.length() == 5
            throw new RuntimeException("testWriteWithOffsetPlusLengthExceedingStringLength failed: expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testWriteWithNullString() {
        StringWriter sw = new StringWriter();
        try {
            sw.write(null, 0, 1);
            throw new RuntimeException("testWriteWithNullString failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
