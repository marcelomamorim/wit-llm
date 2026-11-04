/*
 * @test
 * @summary Verify CharArrayWriter(int initialSize) rejects negative sizes and accepts zero and positive sizes with correct subsequent behavior
 * @run main CharArrayWriterWitupTest
 */

import java.io.CharArrayWriter;

public class CharArrayWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeInitialSizeIsRejected();
        testMinValueInitialSizeIsRejected();
        testZeroInitialSizeGrowsOnWrite();
        testPositiveInitialSizeRetainsWrittenData();
        testZeroSizeWriteCharArrayRange();
    }

    static void testNegativeInitialSizeIsRejected() {
        try {
            new CharArrayWriter(-1);
            throw new RuntimeException("Expected IllegalArgumentException for initialSize -1");
        } catch (IllegalArgumentException e) {
            if (!"Negative initial size: -1".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testMinValueInitialSizeIsRejected() {
        try {
            new CharArrayWriter(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testZeroInitialSizeGrowsOnWrite() {
        CharArrayWriter w = new CharArrayWriter(0);
        w.write('a');
        w.write('b');
        w.write('c');
        String s = new String(w.toCharArray());
        if (!"abc".equals(s)) {
            throw new RuntimeException("Expected 'abc' but got '" + s + "'");
        }
    }

    static void testPositiveInitialSizeRetainsWrittenData() {
        CharArrayWriter w = new CharArrayWriter(2);
        w.write("hello world");
        String s = w.toString();
        if (!"hello world".equals(s)) {
            throw new RuntimeException("Expected 'hello world' but got '" + s + "'");
        }
    }

    static void testZeroSizeWriteCharArrayRange() {
        CharArrayWriter w = new CharArrayWriter(0);
        char[] src = "hello".toCharArray();
        w.write(src, 1, 3);
        String s = w.toString();
        if (!"ell".equals(s)) {
            throw new RuntimeException("Expected 'ell' but got '" + s + "'");
        }
    }
}
