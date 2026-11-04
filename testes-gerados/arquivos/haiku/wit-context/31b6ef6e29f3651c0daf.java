/*
 * @test
 * @summary Checks public ObjectInputStream.readLong behavior in block data mode, including round trips and EOF on a truncated long.
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        long[] values = {0L, 1L, -1L, Long.MAX_VALUE, Long.MIN_VALUE, 0x0102030405060708L};
        for (long v : values) {
            roundTrip(v);
        }
        sequenceTest();
        eofOnTruncatedLongTest();
    }

    static void roundTrip(long v) throws IOException {
        byte[] data = writeLongs(v);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        long r = ois.readLong();
        check(r == v, "round trip failed for " + v + " got " + r);
    }

    static void sequenceTest() throws IOException {
        long a = 42L;
        long b = -7655321L;
        long c = Long.MIN_VALUE + 3;
        byte[] data = writeLongs(a, b, c);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        check(ois.readLong() == a, "first long mismatch");
        check(ois.readLong() == b, "second long mismatch");
        check(ois.readLong() == c, "third long mismatch");
    }

    static void eofOnTruncatedLongTest() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(7);
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        boolean thrown = false;
        try {
            ois.readLong();
        } catch (EOFException e) {
            thrown = true;
        }
        check(thrown, "expected EOFException when reading a long from truncated block data");
    }

    static byte[] writeLongs(long... vs) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        for (long v : vs) {
            oos.writeLong(v);
        }
        oos.close();
        return bos.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
