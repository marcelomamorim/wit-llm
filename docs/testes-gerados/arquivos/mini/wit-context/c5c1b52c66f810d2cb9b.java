/*
 * @test
 * @summary Test ObjectInputStream.readUnsignedByte() for EOFException and normal return
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadUnsignedByteNormal();
        testReadUnsignedByteEOFException();
    }

    private static void testReadUnsignedByteNormal() throws IOException {
        byte[] data = new byte[] { (byte)0x7F, (byte)0x00, (byte)0xFF };
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        int b1 = ois.readUnsignedByte();
        if (b1 != 0x7F) {
            throw new AssertionError("Expected 0x7F but got " + b1);
        }
        int b2 = ois.readUnsignedByte();
        if (b2 != 0x00) {
            throw new AssertionError("Expected 0x00 but got " + b2);
        }
        int b3 = ois.readUnsignedByte();
        if (b3 != 0xFF) {
            throw new AssertionError("Expected 0xFF but got " + b3);
        }
        ois.close();
    }

    private static void testReadUnsignedByteEOFException() throws IOException {
        byte[] empty = new byte[0];
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(empty));
        try {
            ois.readUnsignedByte();
            throw new AssertionError("Expected EOFException not thrown");
        } catch (EOFException e) {
            // Expected exception
        } finally {
            ois.close();
        }
    }
}
