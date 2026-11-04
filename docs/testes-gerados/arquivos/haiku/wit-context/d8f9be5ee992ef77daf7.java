/*
 * @test
 * @summary Witup-generated behavioral tests for ObjectOutputStream.write(byte[])
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSmallRoundTrip();
        testLargeRoundTrip();
        testEmptyArray();
        testNullArgumentThrowsNPE();
    }

    static void testSmallRoundTrip() throws IOException {
        byte[] data = {1, 2, 3, (byte) 0xFF, 0, 42};
        byte[] back = writeAndReadBack(data);
        if (!Arrays.equals(data, back)) {
            throw new RuntimeException("small round trip mismatch");
        }
    }

    static void testLargeRoundTrip() throws IOException {
        byte[] data = new byte[3000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i * 31 + 7);
        }
        byte[] back = writeAndReadBack(data);
        if (!Arrays.equals(data, back)) {
            throw new RuntimeException("large round trip mismatch");
        }
    }

    static void testEmptyArray() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.write(new byte[0]);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        ois.readFully(new byte[0]);
    }

    static void testNullArgumentThrowsNPE() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
        boolean thrown = false;
        try {
            oos.write((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null buffer");
        }
    }

    static byte[] writeAndReadBack(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.write(data);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        byte[] back = new byte[data.length];
        ois.readFully(back);
        return back;
    }
}
