/*
 * @test
 * @summary Test readFully method for normal and exceptional behavior
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyEOFException();
    }

    private static void testReadFullyNormal() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[5];
        ois.readFully(buffer, 0, buffer.length);
        boolean success = true;
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] != data[i]) {
                success = false;
                break;
            }
        }
        if (!success) {
            throw new AssertionError("readFully did not read expected data");
        }
        ois.close();
    }

    private static void testReadFullyEOFException() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            byte[] buffer = new byte[10];
            ois.readFully(buffer, 0, buffer.length);
            ois.close();
            throw new AssertionError("Expected EOFException was not thrown");
        } catch (IOException e) {
            if (!(e instanceof java.io.EOFException)) {
                throw new AssertionError("Expected EOFException, but got: " + e, e);
            }
            // Expected exception
        }
    }
}
