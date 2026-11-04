/*
 * @test
 * @summary Checks public ObjectInputStream.readFully(byte[]) for block data, premature end of stream and null array
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

    public static void main(String[] args) throws Exception {
        testReadFullyReturnsBlockData();
        testReadFullyEofWhenNoBlockData();
        testReadFullyNullArray();
    }

    static void testReadFullyReturnsBlockData() throws IOException {
        byte[] data = {1, 2, 3, 4};
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.write(data);
        }
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
        byte[] out = new byte[data.length];
        ois.readFully(out);
        if (!Arrays.equals(data, out)) {
            throw new AssertionError("readFully returned unexpected content");
        }
    }

    static void testReadFullyEofWhenNoBlockData() throws IOException {
        byte[] header = {(byte) 0xAC, (byte) 0xED, 0, 5};
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(header));
        try {
            ois.readFully(new byte[1]);
        } catch (EOFException expected) {
            return;
        }
        throw new AssertionError("EOFException expected");
    }

    static void testReadFullyNullArray() throws IOException {
        byte[] header = {(byte) 0xAC, (byte) 0xED, 0, 5};
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(header));
        try {
            ois.readFully(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("NullPointerException expected");
    }
}
