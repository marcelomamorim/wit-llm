/*
 * @test
 * @summary ObjectInputStream.readFully(byte[], int, int) bounds checks and block data reads
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputStreamWitupTest {

    static ObjectInputStream newStream(byte[] payload) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.write(payload);
        }
        return new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
    }

    static void expectIOOBE(ObjectInputStream ois, byte[] buf, int off, int len) {
        try {
            ois.readFully(buf, off, len);
        } catch (IndexOutOfBoundsException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Expected IndexOutOfBoundsException, got IOException", e);
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for off=" + off + " len=" + len);
    }

    public static void main(String[] args) throws Exception {
        byte[] data = {1, 2, 3, 4, 5};

        // Valid full read
        try (ObjectInputStream ois = newStream(data)) {
            byte[] buf = new byte[5];
            ois.readFully(buf, 0, 5);
            for (int i = 0; i < data.length; i++) {
                if (buf[i] != data[i]) {
                    throw new RuntimeException("Mismatch at " + i);
                }
            }
        }

        // Valid partial read at offset
        try (ObjectInputStream ois = newStream(data)) {
            byte[] buf = new byte[8];
            ois.readFully(buf, 2, 3);
            if (buf[2] != 1 || buf[3] != 2 || buf[4] != 3 || buf[0] != 0 || buf[7] != 0) {
                throw new RuntimeException("Unexpected partial read result");
            }
        }

        // Zero-length read at end of array is valid and must not fail
        try (ObjectInputStream ois = newStream(data)) {
            byte[] buf = new byte[4];
            ois.readFully(buf, 4, 0);
        }

        // Negative offset
        try (ObjectInputStream ois = newStream(data)) {
            expectIOOBE(ois, new byte[5], -1, 2);
        }

        // Negative length
        try (ObjectInputStream ois = newStream(data)) {
            expectIOOBE(ois, new byte[5], 0, -1);
        }

        // End beyond array length
        try (ObjectInputStream ois = newStream(data)) {
            expectIOOBE(ois, new byte[5], 3, 3);
        }

        // Integer overflow of off + len makes endoff negative
        try (ObjectInputStream ois = newStream(data)) {
            expectIOOBE(ois, new byte[5], Integer.MAX_VALUE, 1);
        }

        // Requesting more bytes than the stream holds
        try (ObjectInputStream ois = newStream(new byte[2])) {
            try {
                ois.readFully(new byte[5], 0, 5);
                throw new RuntimeException("Expected EOFException");
            } catch (EOFException e) {
                // expected
            }
        }
    }
}
