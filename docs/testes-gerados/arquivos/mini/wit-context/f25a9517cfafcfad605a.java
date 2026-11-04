/*
 * @test
 * @summary Test ObjectInputStream.readByte() for EOFException and normal return
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadByteNormal();
        testReadByteEOFException();
    }

    private static void testReadByteNormal() throws IOException {
        byte[] data = {0x01, 0x02, 0x03};
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        for (int i = 0; i < data.length; i++) {
            byte b = ois.readByte();
            if (b != data[i]) {
                throw new AssertionError("Expected byte " + data[i] + " but got " + b);
            }
        }
        ois.close();
    }

    private static void testReadByteEOFException() throws IOException {
        byte[] empty = {};
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(empty));
        try {
            ois.readByte();
            throw new AssertionError("Expected EOFException not thrown");
        } catch (EOFException e) {
            // Expected exception
        } finally {
            ois.close();
        }
    }
}
