/*
 * @test
 * @summary Verifies StringWriter.write(String, int, int) appends the expected substring and rejects invalid ranges
 * @run main StringWriterWitupTest
 */

import java.io.StringWriter;

public class StringWriterWitupTest {

    public static void main(String[] args) {
        testAppendsSubstring();
        testZeroLengthAppendsNothing();
        testFullStringAppend();
        testAppendsAfterExistingContent();
        testInvalidRangesThrow();
        System.out.println("PASSED");
    }

    static void testAppendsSubstring() {
        StringWriter w = new StringWriter();
        w.write("hello world", 6, 5);
        check("world".equals(w.toString()), "expected 'world' but was '" + w + "'");
    }

    static void testZeroLengthAppendsNothing() {
        StringWriter w = new StringWriter();
        w.write("abc", 1, 0);
        check("".equals(w.toString()), "zero-length write at offset 1 should append nothing");
        w.write("abc", 3, 0);
        check("".equals(w.toString()), "zero-length write at end offset should append nothing");
    }

    static void testFullStringAppend() {
        StringWriter w = new StringWriter(2);
        w.write("abcdef", 0, 6);
        check("abcdef".equals(w.toString()), "full-range write should append entire string");
    }

    static void testAppendsAfterExistingContent() {
        StringWriter w = new StringWriter();
        w.write("X");
        w.write("abcdef", 2, 3);
        check("Xcde".equals(w.toString()), "expected 'Xcde' but was '" + w + "'");
    }

    static void testInvalidRangesThrow() {
        expectIOOBE("abc", -1, 2);
        expectIOOBE("abc", 0, -1);
        expectIOOBE("abc", 2, 2);
        expectIOOBE("abc", 1, -2);
        expectIOOBE("abc", 4, 0);
    }

    static void expectIOOBE(String s, int off, int len) {
        StringWriter w = new StringWriter();
        try {
            w.write(s, off, len);
        } catch (IndexOutOfBoundsException e) {
            check("".equals(w.toString()), "buffer must be unchanged after failed write");
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for off=" + off + ", len=" + len);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
