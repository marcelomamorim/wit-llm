/*
 * @test
 * @summary Witup-generated checks for CharArrayWriter(int) constructor argument validation and observable buffer behavior
 * @run main CharArrayWriterWitupTest
 */

import java.io.CharArrayWriter;

public class CharArrayWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeInitialSizeThrows();
        testNegativeMinValueThrows();
        testZeroInitialSizeAcceptsWrites();
        testPositiveInitialSizeAcceptsWrites();
        testDefaultConstructorStartsEmpty();
        testWriteWithInvalidOffsetThrows();
    }

    static void testNegativeInitialSizeThrows() {
        try {
            new CharArrayWriter(-1);
            throw new RuntimeException("Expected IllegalArgumentException for initialSize -1");
        } catch (IllegalArgumentException e) {
            check("Negative initial size: -1".equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    static void testNegativeMinValueThrows() {
        try {
            new CharArrayWriter(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
        } catch (IllegalArgumentException e) {
            check(e.getMessage() != null && e.getMessage().startsWith("Negative initial size: "),
                  "Unexpected message: " + e.getMessage());
        }
    }

    static void testZeroInitialSizeAcceptsWrites() {
        CharArrayWriter w = new CharArrayWriter(0);
        w.write('a');
        check(w.size() == 1, "size after one write expected 1 but was " + w.size());
        check("a".equals(w.toString()), "toString mismatch: " + w.toString());
    }

    static void testPositiveInitialSizeAcceptsWrites() {
        CharArrayWriter w = new CharArrayWriter(1);
        w.write("hello");
        check(w.size() == 5, "size expected 5 but was " + w.size());
        check("hello".equals(w.toString()), "toString mismatch: " + w.toString());
    }

    static void testDefaultConstructorStartsEmpty() {
        CharArrayWriter w = new CharArrayWriter();
        check(w.size() == 0, "default writer size expected 0 but was " + w.size());
        check("".equals(w.toString()), "default writer toString should be empty");
    }

    static void testWriteWithInvalidOffsetThrows() {
        CharArrayWriter w = new CharArrayWriter(4);
        char[] data = {'a', 'b'};
        try {
            w.write(data, 0, 3);
            throw new RuntimeException("Expected IndexOutOfBoundsException for len beyond array");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            w.write(data, -1, 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
