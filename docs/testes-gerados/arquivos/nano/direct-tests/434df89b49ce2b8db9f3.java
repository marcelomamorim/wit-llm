/*
 * @test
 * @summary Tests ObjectInputStream.read(byte[], int, int) method for correct behavior.
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testReadWithValidBuffer();
        testReadWithNullBuffer();
        testReadWithInvalidIndices();
    }

    static void testReadWithValidBuffer() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[3];
        int bytesRead = ois.read(buffer, 0, buffer.length);
        if (bytesRead != 3) {
            throw new AssertionError("Expected to read 3 bytes, but read " + bytesRead);
        }
        for (int i = 0; i < 3; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("Buffer content mismatch at index " + i);
            }
        }
        ois.close();
    }

    static void testReadWithNullBuffer() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[]{1, 2, 3});
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(bais);
            ois.read(null, 0, 1);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    static void testReadWithInvalidIndices() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(bais);
            ois.read(new byte[2], -1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
        try {
            ois = new ObjectInputStream(bais);
            ois.read(new byte[2], 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for negative length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
        try {
            ois = new ObjectInputStream(bais);
            ois.read(new byte[2], 1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown for end offset exceeding buffer length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
