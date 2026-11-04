/*
 * @test
 * @summary Verify ObjectInputStream.readUnsignedByte returns unsigned block data bytes and throws EOFException at end of stream
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeByte(0x00);
            oos.writeByte(0x7F);
            oos.writeByte(0xFF);
            oos.flush();
        }

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));

        if (ois.readUnsignedByte() != 0) {
            throw new AssertionError("expected 0");
        }
        if (ois.readUnsignedByte() != 127) {
            throw new AssertionError("expected 127");
        }
        if (ois.readUnsignedByte() != 255) {
            throw new AssertionError("expected 255 (unsigned)");
        }

        boolean eof = false;
        try {
            ois.readUnsignedByte();
        } catch (EOFException e) {
            eof = true;
        }
        if (!eof) {
            throw new AssertionError("expected EOFException at end of stream");
        }
    }
}
