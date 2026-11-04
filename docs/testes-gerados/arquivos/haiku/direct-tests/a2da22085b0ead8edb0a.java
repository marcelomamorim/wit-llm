/*
 * @test
 * @summary ObjectOutputStream.write(byte[]) delegates to block data writing of the whole array
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteSmallArrayEmitsBlockData();
        testWriteEmptyArrayEmitsNothing();
        testWriteNullThrowsNullPointerException();
    }

    static void testWriteSmallArrayEmitsBlockData() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.flush();
        int base = bos.size();

        byte[] data = {1, 2, 3, (byte) 0xFF};
        oos.write(data);
        oos.flush();

        byte[] out = bos.toByteArray();
        check(out.length == base + 2 + data.length,
              "unexpected output length: " + (out.length - base));
        check(out[base] == (byte) 0x77, "expected TC_BLOCKDATA marker");
        check(out[base + 1] == (byte) data.length, "expected block length");
        for (int i = 0; i < data.length; i++) {
            check(out[base + 2 + i] == data[i], "payload mismatch at " + i);
        }
        oos.close();
    }

    static void testWriteEmptyArrayEmitsNothing() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.flush();
        int base = bos.size();

        oos.write(new byte[0]);
        oos.flush();

        check(bos.size() == base, "empty write must not add bytes");
        oos.close();
    }

    static void testWriteNullThrowsNullPointerException() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        boolean thrown = false;
        try {
            oos.write((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "write(null) must throw NullPointerException");
        oos.close();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
