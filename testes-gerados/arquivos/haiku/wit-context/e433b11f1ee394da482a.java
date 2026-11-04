/*
 * @test
 * @summary Verify DataInputStream.readLong decodes big-endian values and throws EOFException on short input
 * @run main DataInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class DataInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check(new byte[] {1, 2, 3, 4, 5, 6, 7, 8}, 0x0102030405060708L);
        check(new byte[] {-1, -1, -1, -1, -1, -1, -1, -1}, -1L);
        check(new byte[] {0, 0, 0, 0, 0, 0, 0, 0}, 0L);
        check(new byte[] {(byte) 0x80, 0, 0, 0, 0, 0, 0, 0}, Long.MIN_VALUE);
        check(new byte[] {0x7f, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                           (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}, Long.MAX_VALUE);

        expectEof(new byte[0]);
        expectEof(new byte[7]);

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(
                new byte[] {1, 2, 3, 4, 5, 6, 7, 8,
                            0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18}));
        long first = in.readLong();
        long second = in.readLong();
        if (first != 0x0102030405060708L) {
            throw new RuntimeException("first value mismatch: " + Long.toHexString(first));
        }
        if (second != 0x1112131415161718L) {
            throw new RuntimeException("second value mismatch: " + Long.toHexString(second));
        }
        expectEof(new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
    }

    private static void check(byte[] data, long expected) throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        long actual = in.readLong();
        if (actual != expected) {
            throw new RuntimeException("expected " + Long.toHexString(expected)
                    + " but was " + Long.toHexString(actual));
        }
    }

    private static void expectEof(byte[] data) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        try {
            in.readLong();
        } catch (EOFException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException type: " + e, e);
        }
        throw new RuntimeException("expected EOFException for input length " + data.length);
    }
}
