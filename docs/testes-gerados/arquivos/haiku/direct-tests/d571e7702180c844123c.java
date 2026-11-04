/*
 * @test
 * @summary Basic observable behavior of ObjectInputStream.readFully(byte[], int, int)
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class ObjectInputStreamWitupTest {

    static final byte[] PAYLOAD = {1, 2, 3, 4, 5, 6, 7, 8};

    static ObjectInputStream newStream(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.write(data);
        }
        return new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        testReadWholeArray();
        testReadWithOffset();
        testZeroLengthLeavesArrayUnchanged();
        testEofWhenNotEnoughData();
    }

    static void testReadWholeArray() throws IOException {
        ObjectInputStream ois = newStream(PAYLOAD);
        byte[] out = new byte[PAYLOAD.length];
        ois.readFully(out, 0, out.length);
        check(Arrays.equals(PAYLOAD, out), "full read mismatch");
    }

    static void testReadWithOffset() throws IOException {
        ObjectInputStream ois = newStream(PAYLOAD);
        byte[] out = new byte[10];
        ois.readFully(out, 2, 4);
        check(out[0] == 0 && out[1] == 0, "bytes before offset modified");
        check(out[2] == 1 && out[3] == 2 && out[4] == 3 && out[5] == 4, "offset read mismatch");
        check(out[6] == 0 && out[9] == 0, "bytes after range modified");
    }

    static void testZeroLengthLeavesArrayUnchanged() throws IOException {
        ObjectInputStream ois = newStream(PAYLOAD);
        byte[] out = new byte[4];
        ois.readFully(out, 0, 0);
        check(Arrays.equals(new byte[4], out), "zero length read modified array");
    }

    static void testEofWhenNotEnoughData() throws IOException {
        ObjectInputStream ois = newStream(PAYLOAD);
        byte[] out = new byte[PAYLOAD.length + 1];
        try {
            ois.readFully(out, 0, out.length);
            throw new RuntimeException("expected EOFException");
        } catch (EOFException expected) {
            // expected
        }
    }
}
