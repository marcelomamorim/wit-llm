/*
 * @test
 * @summary Witup-derived behavioral tests for ObjectInputStream.readFully(byte[])
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
        testReadFullyReadsExactBytes();
        testReadFullyZeroLengthBuffer();
        testReadFullyPastEndThrowsEOFException();
    }

    private static byte[] serializeBlockData(byte[] payload) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.write(payload);
            oos.flush();
        }
        return bos.toByteArray();
    }

    private static void testReadFullyReadsExactBytes() throws Exception {
        byte[] payload = {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] data = serializeBlockData(payload);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            byte[] buf = new byte[payload.length];
            ois.readFully(buf);
            if (!Arrays.equals(payload, buf)) {
                throw new RuntimeException("readFully returned unexpected bytes: "
                        + Arrays.toString(buf));
            }
        }
    }

    private static void testReadFullyZeroLengthBuffer() throws Exception {
        byte[] payload = {9, 10, 11};
        byte[] data = serializeBlockData(payload);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            byte[] empty = new byte[0];
            ois.readFully(empty);
            byte[] buf = new byte[payload.length];
            ois.readFully(buf);
            if (!Arrays.equals(payload, buf)) {
                throw new RuntimeException("zero-length readFully consumed data: "
                        + Arrays.toString(buf));
            }
        }
    }

    private static void testReadFullyPastEndThrowsEOFException() throws Exception {
        byte[] payload = {1, 2};
        byte[] data = serializeBlockData(payload);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            byte[] buf = new byte[payload.length + 8];
            try {
                ois.readFully(buf);
            } catch (EOFException expected) {
                return;
            }
            throw new RuntimeException("expected EOFException when reading past end of data");
        }
    }
}
