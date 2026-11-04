/*
 * @test
 * @summary Verifies big-endian decoding, sign handling, sequential reads and EOF behavior of DataInputStream.readLong()
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check(0x0102030405060708L, new byte[]{1, 2, 3, 4, 5, 6, 7, 8});
        check(1L, new byte[]{0, 0, 0, 0, 0, 0, 0, 1});
        check(Long.MIN_VALUE, new byte[]{(byte) 0x80, 0, 0, 0, 0, 0, 0, 0});
        check(-1L, new byte[]{-1, -1, -1, -1, -1, -1, -1, -1});
        check(Long.MAX_VALUE, new byte[]{0x7F, -1, -1, -1, -1, -1, -1, -1});
        check(0L, new byte[]{0, 0, 0, 0, 0, 0, 0, 0});
        check(0x00000000FFFFFFFFL, new byte[]{0, 0, 0, 0, -1, -1, -1, -1});

        byte[] twoLongs = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 2,
            0x10, 0x20, 0x30, 0x40, 0x50, 0x60, 0x70, (byte) 0x80
        };
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(twoLongs));
        long first = in.readLong();
        long second = in.readLong();
        if (first != 2L) {
            throw new RuntimeException("first long: expected 2 but was " + first);
        }
        if (second != 0x1020304050607080L) {
            throw new RuntimeException("second long: unexpected value " + second);
        }

        expectEOF(new byte[0]);
        expectEOF(new byte[7]);
    }

    private static void check(long expected, byte[] data) throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        long actual = in.readLong();
        if (actual != expected) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    private static void expectEOF(byte[] data) throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        try {
            in.readLong();
            throw new RuntimeException("expected EOFException for input length " + data.length);
        } catch (EOFException expected) {
            // expected
        }
    }
}
