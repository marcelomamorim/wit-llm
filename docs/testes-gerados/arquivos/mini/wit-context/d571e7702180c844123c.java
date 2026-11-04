/*
 * @test
 * @summary Test deterministic behavior of ObjectInputStream.readFully(byte[], int, int)
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyWithInvalidParameters();
    }

    private static void testReadFullyNormal() throws IOException {
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[5];
        ois.readFully(buffer, 0, 5);
        for (int i = 0; i < 5; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("Data mismatch at index " + i);
            }
        }
        ois.close();
    }

    private static void testReadFullyWithInvalidParameters() throws IOException {
        byte[] data = new byte[] {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[3];
        // Attempt to read more bytes than available to trigger EOFException
        boolean eofThrown = false;
        try {
            ois.readFully(buffer, 0, 4); // len=4 > data.length=3
        } catch (EOFException e) {
            eofThrown = true;
        }
        if (!eofThrown) {
            throw new AssertionError("Expected EOFException was not thrown");
        }
        ois.close();
    }
}
