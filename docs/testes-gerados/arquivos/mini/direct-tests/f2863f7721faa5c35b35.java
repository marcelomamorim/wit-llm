/*
 * @test
 * @summary Test DataInputStream.read(byte[]) method behavior
 * @run main DataInputStreamWitupTest
 */

import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DataInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testReadReturnsCorrectLength();
        testReadReturnsMinusOneOnEmptyStream();
        testReadThrowsNullPointerException();
    }

    private static void testReadReturnsCorrectLength() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[5];
        int readBytes = dis.read(buffer);
        if (readBytes != 5) {
            throw new AssertionError("Expected 5 bytes read, got " + readBytes);
        }
        for (int i = 0; i < readBytes; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("Data mismatch at index " + i);
            }
        }
    }

    private static void testReadReturnsMinusOneOnEmptyStream() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        DataInputStream dis = new DataInputStream(bais);
        byte[] buffer = new byte[10];
        int readBytes = dis.read(buffer);
        if (readBytes != -1) {
            throw new AssertionError("Expected -1 for end of stream, got " + readBytes);
        }
    }

    private static void testReadThrowsNullPointerException() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[10]);
        DataInputStream dis = new DataInputStream(bais);
        try {
            dis.read(null);
            throw new AssertionError("Expected NullPointerException when passing null buffer");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new AssertionError("Expected NullPointerException, got IOException", ioe);
        }
    }
}
