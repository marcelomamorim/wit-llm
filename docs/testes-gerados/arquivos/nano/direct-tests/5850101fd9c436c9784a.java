/*
 * @test
 * @summary Test ObjectInputStream.readFully(byte[] b) for correct behavior.
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadFullyWithValidInput();
        testReadFullyWithEmptyArray();
        testReadFullyWithNullArray();
    }

    static void testReadFullyWithValidInput() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[5];
        ois.readFully(buffer);
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

    static void testReadFullyWithEmptyArray() throws IOException {
        byte[] data = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[0];
        ois.readFully(buffer);
        ois.close();
    }

    static void testReadFullyWithNullArray() {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{1});
            ObjectInputStream ois = new ObjectInputStream(bais);
            ois.readFully(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
