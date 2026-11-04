/*
 * @test
 * @summary Test for ObjectInputStream.readFully(byte[] buf)
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyEOF();
    }

    static void testReadFullyNormal() throws IOException {
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

    static void testReadFullyEOF() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            byte[] buf = new byte[5];
            ois.readFully(buf);
            throw new AssertionError("Expected EOFException was not thrown");
        } catch (IOException e) {
            if (!(e instanceof java.io.EOFException)) {
                throw new AssertionError("Unexpected exception: " + e, e);
            }
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
