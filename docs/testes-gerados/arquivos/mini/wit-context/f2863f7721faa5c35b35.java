/*
 * @test
 * @summary Test DataInputStream.read(byte[]) method for correct delegation and boundary conditions
 * @run main DataInputStreamWitupTest
 */

import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DataInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testReadDelegation();
        testReadWithEmptyArray();
        testReadWithNullArray();
    }

    private static void testReadDelegation() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);

        byte[] buffer = new byte[5];
        int readCount = dis.read(buffer);

        if (readCount != 5) {
            throw new AssertionError("Expected to read 5 bytes, but read " + readCount);
        }
        for (int i = 0; i < readCount; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("Byte at index " + i + " differs: expected " + data[i] + ", got " + buffer[i]);
            }
        }
    }

    private static void testReadWithEmptyArray() throws IOException {
        byte[] data = {10, 20, 30};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);

        byte[] emptyBuffer = new byte[0];
        int readCount = dis.read(emptyBuffer);

        if (readCount != 0) {
            throw new AssertionError("Expected to read 0 bytes for empty array, but read " + readCount);
        }
    }

    private static void testReadWithNullArray() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        DataInputStream dis = new DataInputStream(bais);

        try {
            dis.read(null);
            throw new AssertionError("Expected NullPointerException when reading into null array");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException instead of NullPointerException", e);
        }
    }
}
