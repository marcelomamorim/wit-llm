/*
 * @test
 * @summary Test StringWriter.write(char[], int, int) for valid and invalid inputs
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {
    public static void main(String[] args) {
        testWriteValid();
        testWriteZeroLength();
        testWriteInvalidOffsets();
    }

    private static void testWriteValid() {
        StringWriter sw = new StringWriter();
        char[] data = new char[] {'a', 'b', 'c', 'd', 'e'};
        sw.write(data, 1, 3); // should write 'b', 'c', 'd'
        String result = sw.toString();
        if (!"bcd".equals(result)) {
            throw new RuntimeException("testWriteValid failed: expected 'bcd' but got '" + result + "'");
        }
    }

    private static void testWriteZeroLength() {
        StringWriter sw = new StringWriter();
        char[] data = new char[] {'x', 'y', 'z'};
        sw.write(data, 1, 0); // should write nothing
        String result = sw.toString();
        if (!"".equals(result)) {
            throw new RuntimeException("testWriteZeroLength failed: expected empty string but got '" + result + "'");
        }
    }

    private static void testWriteInvalidOffsets() {
        StringWriter sw = new StringWriter();
        char[] data = new char[] {'1', '2', '3'};

        // off < 0
        try {
            sw.write(data, -1, 1);
            throw new RuntimeException("testWriteInvalidOffsets failed: expected IndexOutOfBoundsException for off < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // off > cbuf.length
        try {
            sw.write(data, 4, 1);
            throw new RuntimeException("testWriteInvalidOffsets failed: expected IndexOutOfBoundsException for off > cbuf.length");
        } catch (IndexOutOfBoundsException expected) {
        }

        // len < 0
        try {
            sw.write(data, 0, -1);
            throw new RuntimeException("testWriteInvalidOffsets failed: expected IndexOutOfBoundsException for len < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // off + len > cbuf.length
        try {
            sw.write(data, 2, 2);
            throw new RuntimeException("testWriteInvalidOffsets failed: expected IndexOutOfBoundsException for off + len > cbuf.length");
        } catch (IndexOutOfBoundsException expected) {
        }

        // off + len < 0 (overflow case)
        try {
            sw.write(data, Integer.MAX_VALUE, Integer.MIN_VALUE);
            throw new RuntimeException("testWriteInvalidOffsets failed: expected IndexOutOfBoundsException for off + len < 0");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
