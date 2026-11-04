/*
 * @test
 * @summary WITUP-derived behavioral tests for InputStream.read(byte[], int, int)
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullArrayRejected();
        testOutOfBoundsRejected();
        testZeroLengthReturnsZero();
        testReadsUntilEndOfStream();
        testEofOnFirstByte();
        testNullInputStreamEof();
        testNullInputStreamClosedThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static InputStream sequence(final int... values) {
        return new InputStream() {
            private int idx = 0;

            @Override
            public int read() {
                if (idx >= values.length) {
                    return -1;
                }
                return values[idx++];
            }
        };
    }

    static void testNullArrayRejected() throws IOException {
        boolean thrown = false;
        try {
            sequence(1).read(null, 0, 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null array must raise NullPointerException");
    }

    static void testOutOfBoundsRejected() throws IOException {
        byte[] b = new byte[4];
        boolean t1 = false;
        try {
            sequence(1).read(b, -1, 2);
        } catch (IndexOutOfBoundsException e) {
            t1 = true;
        }
        check(t1, "negative off must raise IndexOutOfBoundsException");

        boolean t2 = false;
        try {
            sequence(1).read(b, 3, 2);
        } catch (IndexOutOfBoundsException e) {
            t2 = true;
        }
        check(t2, "off+len beyond length must raise IndexOutOfBoundsException");

        boolean t3 = false;
        try {
            sequence(1).read(b, 0, -1);
        } catch (IndexOutOfBoundsException e) {
            t3 = true;
        }
        check(t3, "negative len must raise IndexOutOfBoundsException");
    }

    static void testZeroLengthReturnsZero() throws IOException {
        InputStream in = sequence(1, 2);
        byte[] b = new byte[4];
        check(in.read(b, 2, 0) == 0, "len == 0 must return 0");
        check(in.read() == 1, "len == 0 must not consume input");
    }

    static void testReadsUntilEndOfStream() throws IOException {
        byte[] b = new byte[5];
        int n = sequence(1, 2, 3).read(b, 0, 5);
        check(n == 3, "expected 3 bytes read, got " + n);
        check(b[0] == 1 && b[1] == 2 && b[2] == 3, "unexpected buffer contents");
    }

    static void testEofOnFirstByte() throws IOException {
        byte[] b = new byte[4];
        check(sequence().read(b, 0, 4) == -1, "EOF on first byte must return -1");
    }

    static void testNullInputStreamEof() throws IOException {
        InputStream in = InputStream.nullInputStream();
        byte[] b = new byte[4];
        check(in.read(b, 0, 4) == -1, "nullInputStream must report end of stream");
        in.close();
    }

    static void testNullInputStreamClosedThrows() throws IOException {
        InputStream in = InputStream.nullInputStream();
        in.close();
        byte[] b = new byte[4];
        boolean thrown = false;
        try {
            in.read(b, 0, 4);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "read on closed nullInputStream must raise IOException");
    }
}
