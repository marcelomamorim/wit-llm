/*
 * @test
 * @summary Verifies StringWriter.write(char[], int, int) range checks, zero-length writes, and appending behavior
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {

    public static void main(String[] args) {
        testWritesRange();
        testZeroLengthAtEnd();
        testZeroLengthAtStart();
        testAppendsToExisting();
        testOutOfBoundsRejected();
    }

    static void testWritesRange() {
        StringWriter sw = new StringWriter();
        char[] data = {'a', 'b', 'c', 'd'};
        sw.write(data, 1, 2);
        check("bc".equals(sw.toString()), "expected 'bc' but was '" + sw + "'");
    }

    static void testZeroLengthAtEnd() {
        StringWriter sw = new StringWriter();
        char[] data = {'a', 'b'};
        sw.write(data, data.length, 0);
        check(sw.toString().isEmpty(), "zero-length write at end should append nothing");
    }

    static void testZeroLengthAtStart() {
        StringWriter sw = new StringWriter();
        char[] data = {'x'};
        sw.write(data, 0, 0);
        check(sw.toString().isEmpty(), "zero-length write should append nothing");
    }

    static void testAppendsToExisting() {
        StringWriter sw = new StringWriter();
        sw.write("hi");
        char[] data = {'a', 'b', 'c'};
        sw.write(data, 0, 2);
        check("hiab".equals(sw.toString()), "expected 'hiab' but was '" + sw + "'");
    }

    static void testOutOfBoundsRejected() {
        char[] data = {'a', 'b', 'c'};
        expectIOOBE(new StringWriter(), data, -1, 1);
        expectIOOBE(new StringWriter(), data, 0, -1);
        expectIOOBE(new StringWriter(), data, 2, 2);
        expectIOOBE(new StringWriter(), data, 4, 0);
        expectIOOBE(new StringWriter(), data, 1, Integer.MAX_VALUE);
    }

    static void expectIOOBE(StringWriter sw, char[] data, int off, int len) {
        try {
            sw.write(data, off, len);
        } catch (IndexOutOfBoundsException e) {
            check(sw.toString().isEmpty(), "rejected write must not modify the writer");
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for off=" + off + " len=" + len);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
