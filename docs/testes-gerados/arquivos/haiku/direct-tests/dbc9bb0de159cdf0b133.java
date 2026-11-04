/*
 * @test
 * @summary Verify ObjectInputStream.readFully(byte[]) reads exactly buf.length bytes from the stream and throws EOFException when data is insufficient
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

    static byte[] serialize(byte[] payload) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.write(payload);
        oos.flush();
        oos.close();
        return bos.toByteArray();
    }

    static ObjectInputStream open(byte[] data) throws IOException {
        return new ObjectInputStream(new ByteArrayInputStream(data));
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] payload = {1, 2, 3, 4, 5};
        try (ObjectInputStream in = open(serialize(payload))) {
            byte[] buf = new byte[5];
            in.readFully(buf);
            check(Arrays.equals(payload, buf), "readFully did not return the written bytes");
        }

        byte[] seq = {10, 11, 12, 13, 14, 15};
        try (ObjectInputStream in = open(serialize(seq))) {
            byte[] first = new byte[3];
            byte[] second = new byte[3];
            in.readFully(first);
            in.readFully(second);
            check(Arrays.equals(new byte[]{10, 11, 12}, first), "first chunk mismatch");
            check(Arrays.equals(new byte[]{13, 14, 15}, second), "second chunk mismatch");
        }

        try (ObjectInputStream in = open(serialize(new byte[0]))) {
            in.readFully(new byte[0]);
        }

        try (ObjectInputStream in = open(serialize(new byte[]{1, 2, 3}))) {
            boolean thrown = false;
            try {
                in.readFully(new byte[10]);
            } catch (EOFException e) {
                thrown = true;
            }
            check(thrown, "expected EOFException when fewer bytes than buffer length are available");
        }
    }
}
