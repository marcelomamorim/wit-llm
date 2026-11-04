/*
 * @test
 * @summary ObjectInputStream.readFully(byte[]) reads exactly the requested block data, handles zero-length arrays, and throws EOFException when data is exhausted
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
        testReadsExactBytes();
        testZeroLengthDoesNotConsume();
        testEofThrows();
    }

    static byte[] payload(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.write(data);
        }
        return bos.toByteArray();
    }

    static void testReadsExactBytes() throws Exception {
        byte[] data = {1, 2, 3, 4, 5, 6, 7, 8};
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(payload(data)));
        byte[] buf = new byte[data.length];
        ois.readFully(buf);
        for (int i = 0; i < data.length; i++) {
            if (buf[i] != data[i]) {
                throw new RuntimeException("Mismatch at index " + i
                        + ": expected " + data[i] + " got " + buf[i]);
            }
        }
    }

    static void testZeroLengthDoesNotConsume() throws Exception {
        byte[] data = {9};
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(payload(data)));
        ois.readFully(new byte[0]);
        byte[] buf = new byte[1];
        ois.readFully(buf);
        if (buf[0] != 9) {
            throw new RuntimeException("Zero-length read consumed data: got " + buf[0]);
        }
    }

    static void testEofThrows() throws Exception {
        byte[] data = {1, 2};
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(payload(data)));
        try {
            ois.readFully(new byte[4]);
        } catch (EOFException e) {
            return;
        }
        throw new RuntimeException("Expected EOFException when reading past end of data");
    }
}
