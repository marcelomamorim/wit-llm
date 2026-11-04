/*
 * @test
 * @summary Test ObjectInputStream.readByte() behavior including EOFException
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadByteReturnsCorrectValue();
        testReadByteThrowsEOFException();
    }

    private static void testReadByteReturnsCorrectValue() throws IOException {
        byte[] data = new byte[] { (byte)0x7F, (byte)0x80, (byte)0xFF };
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);

        // read first byte: 0x7F (127 decimal)
        byte b1 = ois.readByte();
        if (b1 != 0x7F) {
            throw new AssertionError("Expected 0x7F but got " + b1);
        }

        // read second byte: 0x80 (-128 signed byte)
        byte b2 = ois.readByte();
        if (b2 != (byte)0x80) {
            throw new AssertionError("Expected 0x80 but got " + b2);
        }

        // read third byte: 0xFF (-1 signed byte)
        byte b3 = ois.readByte();
        if (b3 != (byte)0xFF) {
            throw new AssertionError("Expected 0xFF but got " + b3);
        }

        ois.close();
    }

    private static void testReadByteThrowsEOFException() throws IOException {
        byte[] empty = new byte[0];
        ByteArrayInputStream bais = new ByteArrayInputStream(empty);
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            ois.readByte();
            throw new AssertionError("Expected EOFException not thrown");
        } catch (EOFException expected) {
            // expected
        } finally {
            ois.close();
        }
    }
}
