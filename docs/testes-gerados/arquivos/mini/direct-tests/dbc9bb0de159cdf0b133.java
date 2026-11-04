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
        byte[] inputData = new byte[] {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(inputData);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buf = new byte[5];
        ois.readFully(buf);
        for (int i = 0; i < buf.length; i++) {
            if (buf[i] != inputData[i]) {
                throw new AssertionError("readFully did not read expected data at index " + i);
            }
        }
        ois.close();
    }

    private static void testReadFullyWithEmptyArray() throws IOException {
        byte[] inputData = new byte[0];
        ByteArrayInputStream bais = new ByteArrayInputStream(inputData);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buf = new byte[0];
        ois.readFully(buf);
        // No exception expected, nothing to read
        ois.close();
    }

    private static void testReadFullyWithNullArray() {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1});
            ObjectInputStream ois = new ObjectInputStream(bais);
            ois.readFully(null);
            throw new AssertionError("Expected NullPointerException for null buffer");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for null buffer", e);
        }
    }
}
