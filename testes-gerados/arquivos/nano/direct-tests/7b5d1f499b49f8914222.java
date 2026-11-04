/* @test    @summary Test StringWriter.write(char[], int, int) method for correct behavior and exception handling    @run main StringWriterWitupTest */

import java.io.StringWriter;

public class StringWriterWitupTest {

    public static void main(String[] args) {
        testWriteValidRange();
        testWriteNegativeOffset();
        testWriteOffsetGreaterThanArray();
        testWriteNegativeLength();
        testWriteLengthExceedsArray();
        testWriteZeroLength();
    }

    static void testWriteValidRange() {
        StringWriter sw = new StringWriter();
        char[] buffer = {'a', 'b', 'c', 'd', 'e'};
        sw.write(buffer, 1, 3); // should append 'b', 'c', 'd'
        assert sw.toString().equals("bcd") : "Expected 'bcd', got '" + sw.toString() + "'";
    }

    static void testWriteNegativeOffset() {
        StringWriter sw = new StringWriter();
        char[] buffer = {'x', 'y', 'z'};
        boolean exceptionThrown = false;
        try {
            sw.write(buffer, -1, 2);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for negative offset";
    }

    static void testWriteOffsetGreaterThanArray() {
        StringWriter sw = new StringWriter();
        char[] buffer = {'x', 'y', 'z'};
        boolean exceptionThrown = false;
        try {
            sw.write(buffer, 4, 1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for offset greater than array length";
    }

    static void testWriteNegativeLength() {
        StringWriter sw = new StringWriter();
        char[] buffer = {'a', 'b'};
        boolean exceptionThrown = false;
        try {
            sw.write(buffer, 0, -1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for negative length";
    }

    static void testWriteLengthExceedsArray() {
        StringWriter sw = new StringWriter();
        char[] buffer = {'a', 'b'};
        boolean exceptionThrown = false;
        try {
            sw.write(buffer, 1, 2); // off=1, len=2, total=3 > buffer.length=2
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for length exceeding array bounds";
    }

    static void testWriteZeroLength() {
        StringWriter sw = new StringWriter();
        char[] buffer = {'a', 'b'};
        sw.write(buffer, 0, 0); // should do nothing
        assert sw.toString().equals("") : "Expected empty string, got '" + sw.toString() + "'";
    }
}
