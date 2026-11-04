/*
 * @test
 * @summary ObjectInputStream.readUnsignedByte returns unsigned byte values and throws EOFException at end of stream
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] data = {(byte) 0xFF, 0x7F, (byte) 0x80, 0x00};
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.write(data);
        }

        try (ObjectInputStream ois =
                 new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()))) {
            check(ois.readUnsignedByte() == 255, "expected 255");
            check(ois.readUnsignedByte() == 127, "expected 127");
            check(ois.readUnsignedByte() == 128, "expected 128");
            check(ois.readUnsignedByte() == 0, "expected 0");

            boolean eof = false;
            try {
                ois.readUnsignedByte();
            } catch (EOFException e) {
                eof = true;
            }
            check(eof, "expected EOFException at end of stream");
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
