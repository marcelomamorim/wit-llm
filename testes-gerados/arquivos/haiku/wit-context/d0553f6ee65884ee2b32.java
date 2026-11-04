/*
 * @test
 * @summary Verify DataInputStream.readFully(byte[]) fills arrays, handles empty arrays, EOF and null input
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testFullRead();
        testEmptyArrayDoesNotConsume();
        testEofBeforeArrayFilled();
        testEmptyInputEmptyArray();
        testNullArray();
        testChunkedReads();
        testSequentialCalls();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFullRead() throws IOException {
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(new byte[] {1, 2, 3, 4}));
        byte[] b = new byte[4];
        in.readFully(b);
        check(b[0] == 1 && b[1] == 2 && b[2] == 3 && b[3] == 4, "full read content mismatch");
        check(in.read() == -1, "stream should be exhausted after full read");
    }

    static void testEmptyArrayDoesNotConsume() throws IOException {
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(new byte[] {9, 8}));
        in.readFully(new byte[0]);
        check(in.read() == 9, "empty readFully must not consume input");
    }

    static void testEofBeforeArrayFilled() throws IOException {
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(new byte[] {1, 2}));
        byte[] b = new byte[4];
        boolean thrown = false;
        try {
            in.readFully(b);
        } catch (EOFException e) {
            thrown = true;
        }
        check(thrown, "expected EOFException when input is shorter than array");
    }

    static void testEmptyInputEmptyArray() throws IOException {
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(new byte[0]));
        in.readFully(new byte[0]);
    }

    static void testNullArray() throws IOException {
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(new byte[] {1}));
        boolean thrown = false;
        try {
            in.readFully((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }

    static void testChunkedReads() throws IOException {
        DataInputStream in = new DataInputStream(
                new OneByteStream(new byte[] {10, 20, 30, 40, 50}));
        byte[] b = new byte[5];
        in.readFully(b);
        check(b[0] == 10 && b[1] == 20 && b[2] == 30 && b[3] == 40 && b[4] == 50,
                "chunked read content mismatch");
    }

    static void testSequentialCalls() throws IOException {
        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(new byte[] {1, 2, 3, 4, 5, 6}));
        byte[] first = new byte[3];
        byte[] second = new byte[3];
        in.readFully(first);
        in.readFully(second);
        check(first[0] == 1 && first[1] == 2 && first[2] == 3, "first sequential read mismatch");
        check(second[0] == 4 && second[1] == 5 && second[2] == 6, "second sequential read mismatch");
    }

    static class OneByteStream extends InputStream {
        private final byte[] data;
        private int pos;

        OneByteStream(byte[] data) {
            this.data = data;
        }

        @Override
        public int read() {
            return pos < data.length ? (data[pos++] & 0xFF) : -1;
        }

        @Override
        public int read(byte[] b, int off, int len) {
            if (len == 0) {
                return 0;
            }
            int v = read();
            if (v < 0) {
                return -1;
            }
            b[off] = (byte) v;
            return 1;
        }
    }
}
