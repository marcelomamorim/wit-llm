/*
 * @test
 * @summary Test ObjectInputStream.readFully(byte[]) with various byte arrays
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadFullyWithNonEmptyArray();
        testReadFullyWithEmptyArray();
        testReadFullyWithNullArray();
    }

    private static void testReadFullyWithNonEmptyArray() throws IOException {
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[5];
        ois.readFully(buffer);
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("readFully did not read expected data at index " + i);
            }
        }
        ois.close();
    }

    private static void testReadFullyWithEmptyArray() throws IOException {
        byte[] data = new byte[] {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[0];
        ois.readFully(buffer); // should not throw and do nothing
        ois.close();
    }

    private static void testReadFullyWithNullArray() {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1});
            ObjectInputStream ois = new ObjectInputStream(bais);
            ois.readFully(null);
            throw new AssertionError("readFully(null) did not throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException when reading null array", e);
        }
    }
}
