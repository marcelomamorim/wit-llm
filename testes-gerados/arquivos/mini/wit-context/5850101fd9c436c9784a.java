/*
 * @test
 * @summary Test ObjectInputStream.readFully(byte[]) for expected behavior and EOFException
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyEOFException();
        System.out.println("All tests passed.");
    }

    private static void testReadFullyNormal() throws IOException {
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[data.length];
        ois.readFully(buffer);
        for (int i = 0; i < data.length; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("Data mismatch at index " + i);
            }
        }
        ois.close();
    }

    private static void testReadFullyEOFException() throws IOException {
        byte[] data = new byte[] {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[5]; // larger than available data
        boolean threw = false;
        try {
            ois.readFully(buffer);
        } catch (EOFException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected EOFException was not thrown");
        }
        ois.close();
    }
}
