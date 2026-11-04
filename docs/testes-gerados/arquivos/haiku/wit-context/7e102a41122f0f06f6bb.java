/*
 * @test
 * @summary Verify ObjectInputStream.readBoolean decodes written booleans and bytes and throws EOFException at end of data
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
        testWrittenBooleans();
        testNonZeroBytesAreTrue();
        testZeroByteIsFalse();
        testEofAfterAllDataConsumed();
    }

    static void testWrittenBooleans() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeBoolean(true);
        oos.writeBoolean(false);
        oos.close();
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        if (in.readBoolean() != true) {
            throw new RuntimeException("expected true");
        }
        if (in.readBoolean() != false) {
            throw new RuntimeException("expected false");
        }
    }

    static void testNonZeroBytesAreTrue() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeByte(2);
        oos.writeByte(255);
        oos.close();
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        if (in.readBoolean() != true) {
            throw new RuntimeException("byte 2 should decode as true");
        }
        if (in.readBoolean() != true) {
            throw new RuntimeException("byte 255 should decode as true");
        }
    }

    static void testZeroByteIsFalse() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeByte(0);
        oos.close();
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        if (in.readBoolean() != false) {
            throw new RuntimeException("byte 0 should decode as false");
        }
    }

    static void testEofAfterAllDataConsumed() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeBoolean(true);
        oos.close();
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        in.readBoolean();
        try {
            in.readBoolean();
            throw new RuntimeException("expected EOFException");
        } catch (EOFException expected) {
            // expected
        }
    }
}
