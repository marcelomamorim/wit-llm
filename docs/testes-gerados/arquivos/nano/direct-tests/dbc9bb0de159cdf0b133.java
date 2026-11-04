/*
 * @test
 * @summary Test ObjectInputStream.readFully(byte[] buf)
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadFullyWithValidBuffer();
        testReadFullyWithEmptyBuffer();
        testReadFullyWithSmallBuffer();
    }

    static void testReadFullyWithValidBuffer() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buf = new byte[5];
        ois.readFully(buf);
        boolean success = true;
        for (int i = 0; i < buf.length; i++) {
            if (buf[i] != data[i]) {
                success = false;
                break;
            }
        }
        if (!success) {
            throw new AssertionError("readFully did not read expected data");
        }
        ois.close();
    }

    static void testReadFullyWithEmptyBuffer() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buf = new byte[0];
        ois.readFully(buf);
        ois.close();
    }

    static void testReadFullyWithSmallBuffer() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buf = new byte[2];
        try {
            ois.readFully(buf);
            throw new AssertionError("Expected IOException for insufficient data");
        } catch (IOException e) {
            // Expected exception
        }
        ois.close();
    }
}
