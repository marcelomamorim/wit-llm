/*
 * @test
 * @summary Round-trip and truncated-stream behavior of ObjectInputStream.readLong()
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputStreamWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static byte[] writeLongs(long... values) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        for (long v : values) {
            oos.writeLong(v);
        }
        oos.close();
        return bos.toByteArray();
    }

    static void testRoundTrip() throws IOException {
        long[] values = { 0L, 1L, -1L, 42L, Long.MIN_VALUE, Long.MAX_VALUE, 0x0102030405060708L };
        byte[] data = writeLongs(values);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        for (int i = 0; i < values.length; i++) {
            long got = ois.readLong();
            check(got == values[i], "round trip index " + i + " expected " + values[i] + " got " + got);
        }
        ois.close();
    }

    static void testMixedWithInt() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(7);
        oos.writeLong(123456789012345L);
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        check(ois.readInt() == 7, "readInt before readLong");
        check(ois.readLong() == 123456789012345L, "readLong after readInt");
        ois.close();
    }

    static void testTruncatedStream() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(1);
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        ois.readInt();
        boolean eof = false;
        try {
            ois.readLong();
        } catch (EOFException e) {
            eof = true;
        }
        check(eof, "readLong on exhausted stream should throw EOFException");
    }

    public static void main(String[] args) throws Exception {
        testRoundTrip();
        testMixedWithInt();
        testTruncatedStream();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
