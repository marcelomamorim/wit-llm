/*
 * @test
 * @summary Witup-generated tests for ObjectOutputStream.write(byte[])
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullArrayThrowsNPE();
        testRoundTripNonEmptyArray();
        testEmptyArrayAndSubsequentWrite();
    }

    static void testNullArrayThrowsNPE() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        boolean thrown = false;
        try {
            oos.write((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("write(null) should throw NullPointerException");
        }
    }

    static void testRoundTripNonEmptyArray() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.write(data);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        byte[] out = new byte[data.length];
        ois.readFully(out);
        for (int i = 0; i < data.length; i++) {
            if (out[i] != data[i]) {
                throw new AssertionError("mismatch at index " + i + ": expected " + data[i] + " got " + out[i]);
            }
        }
    }

    static void testEmptyArrayAndSubsequentWrite() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.write(new byte[0]);
        oos.write(new byte[] {9});
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        byte[] out = new byte[1];
        ois.readFully(out);
        if (out[0] != 9) {
            throw new AssertionError("expected 9 after empty write, got " + out[0]);
        }
    }
}
