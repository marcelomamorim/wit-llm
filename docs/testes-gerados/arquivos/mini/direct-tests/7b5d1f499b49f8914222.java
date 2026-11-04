/*
 * @test
 * @summary Test java.io.StringWriter.write(char[], int, int) method
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {
    public static void main(String[] args) {
        testValidWrite();
        testZeroLengthWrite();
        testNegativeOffset();
        testNegativeLength();
        testOffsetPlusLengthTooLarge();
        testOffsetTooLarge();
        testOffsetPlusLengthNegative();
    }

    private static void testValidWrite() {
        StringWriter sw = new StringWriter();
        char[] data = {'a', 'b', 'c', 'd', 'e'};
        sw.write(data, 1, 3); // should write 'b', 'c', 'd'
        String result = sw.toString();
        if (!"bcd".equals(result)) {
            throw new RuntimeException("testValidWrite failed: expected 'bcd' but got '" + result + "'");
        }
    }

    private static void testZeroLengthWrite() {
        StringWriter sw = new StringWriter();
        char[] data = {'x', 'y', 'z'};
        sw.write(data, 1, 0); // should write nothing
        String result = sw.toString();
        if (!"".equals(result)) {
            throw new RuntimeException("testZeroLengthWrite failed: expected empty string but got '" + result + "'");
        }
    }

    private static void testNegativeOffset() {
        StringWriter sw = new StringWriter();
        char[] data = {'a', 'b'};
        try {
            sw.write(data, -1, 1);
            throw new RuntimeException("testNegativeOffset failed: expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testNegativeLength() {
        StringWriter sw = new StringWriter();
        char[] data = {'a', 'b'};
        try {
            sw.write(data, 0, -1);
            throw new RuntimeException("testNegativeLength failed: expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testOffsetPlusLengthTooLarge() {
        StringWriter sw = new StringWriter();
        char[] data = {'a', 'b', 'c'};
        try {
            sw.write(data, 1, 3); // 1 + 3 = 4 > length 3
            throw new RuntimeException("testOffsetPlusLengthTooLarge failed: expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testOffsetTooLarge() {
        StringWriter sw = new StringWriter();
        char[] data = {'a', 'b'};
        try {
            sw.write(data, 3, 0); // offset 3 > length 2
            throw new RuntimeException("testOffsetTooLarge failed: expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testOffsetPlusLengthNegative() {
        StringWriter sw = new StringWriter();
        char[] data = {'a', 'b'};
        try {
            sw.write(data, Integer.MAX_VALUE, Integer.MIN_VALUE); // off+len < 0
            throw new RuntimeException("testOffsetPlusLengthNegative failed: expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
