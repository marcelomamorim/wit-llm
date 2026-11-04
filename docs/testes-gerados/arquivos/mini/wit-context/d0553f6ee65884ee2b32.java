/*
 * @test
 * @summary Test DataInputStream.readFully(byte[]) for normal and edge cases
 * @run main DataInputStreamWitupTest
 */

import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;

public class DataInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadFullyNormal();
        testReadFullyEmptyArray();
        testReadFullyNullArray();
    }

    private static void testReadFullyNormal() throws IOException {
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        byte[] buffer = new byte[5];
        dis.readFully(buffer);
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("readFully did not read expected data at index " + i);
            }
        }
    }

    private static void testReadFullyEmptyArray() throws IOException {
        byte[] data = new byte[] {1, 2, 3};
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        byte[] buffer = new byte[0];
        // Should return immediately without exception
        dis.readFully(buffer);
    }

    private static void testReadFullyNullArray() {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(new byte[0]));
        try {
            dis.readFully(null);
            throw new AssertionError("readFully(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException ioe) {
            throw new AssertionError("readFully(null) threw IOException instead of NullPointerException");
        }
    }
}
