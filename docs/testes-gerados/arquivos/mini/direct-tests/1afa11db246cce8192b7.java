/*
 * @test
 * @summary Test ObjectInputStream.readFully(byte[], int, int) for boundary and error conditions
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadFullyValid();
        testReadFullyIndexOutOfBounds();
    }

    private static void testReadFullyValid() throws Exception {
        byte[] data = new byte[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);

        byte[] buf = new byte[10];
        ois.readFully(buf, 0, 10);

        for (int i = 0; i < 10; i++) {
            if (buf[i] != (byte) i) {
                throw new RuntimeException("testReadFullyValid: buf[" + i + "] expected " + i + " but was " + buf[i]);
            }
        }

        ois.close();
    }

    private static void testReadFullyIndexOutOfBounds() throws Exception {
        byte[] data = new byte[5];
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);

        byte[] buf = new byte[5];

        // off < 0
        try {
            ois.readFully(buf, -1, 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for off < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // len < 0
        try {
            ois.readFully(buf, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for len < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // off + len > buf.length
        try {
            ois.readFully(buf, 3, 3);
            throw new RuntimeException("Expected IndexOutOfBoundsException for off + len > buf.length");
        } catch (IndexOutOfBoundsException expected) {
        }

        // off + len < 0 (integer overflow)
        try {
            ois.readFully(buf, Integer.MAX_VALUE, 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for off + len overflow");
        } catch (IndexOutOfBoundsException expected) {
        }

        ois.close();
    }
}
