/*
 * @test
 * @summary Test ObjectInputStream.readFully(byte[], int, int) for correct behavior
 * @run main ObjectInputStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testReadFullyNormal();
        testReadFullyInvalidBounds();
    }

    static void testReadFullyNormal() throws IOException {
        byte[] data = {10, 20, 30, 40, 50};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] buffer = new byte[3];
        ois.readFully(buffer, 0, buffer.length);
        boolean result = (buffer[0] == 10 && buffer[1] == 20 && buffer[2] == 30);
        if (!result) {
            throw new AssertionError("readFully did not read expected bytes");
        }
        ois.close();
    }

    static void testReadFullyInvalidBounds() {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] buffer = new byte[2];
        try {
            ois.readFully(buffer, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
        try {
            ois.readFully(buffer, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
        try {
            ois.readFully(buffer, 0, 5); // buffer length is 2, end offset 5 > 3
            throw new AssertionError("Expected IndexOutOfBoundsException for end offset > buffer length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
        try {
            ois.readFully(buffer, 2, 2); // end offset 4 > buffer length 2
            throw new AssertionError("Expected IndexOutOfBoundsException for end offset > buffer length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
        try {
            ois.readFully(buffer, 0, 3); // valid, should read 3 bytes
            // no exception expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
        try {
            ois.readFully(buffer, 0, 4); // end offset 4 > buffer length 2
            throw new AssertionError("Expected IndexOutOfBoundsException for end offset > buffer length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
        try {
            ois.close();
        } catch (IOException e) {
            throw new AssertionError("Error closing stream", e);
        }
    }
}
