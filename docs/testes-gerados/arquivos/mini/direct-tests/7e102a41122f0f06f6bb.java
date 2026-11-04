/*
 * @test
 * @summary Test ObjectInputStream.readBoolean() behavior including EOFException
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadBooleanTrue();
        testReadBooleanFalse();
        testReadBooleanEOF();
    }

    private static void testReadBooleanTrue() throws IOException {
        byte[] data = {1};
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        boolean result = ois.readBoolean();
        if (!result) {
            throw new AssertionError("Expected true but got false");
        }
        ois.close();
    }

    private static void testReadBooleanFalse() throws IOException {
        byte[] data = {0};
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        boolean result = ois.readBoolean();
        if (result) {
            throw new AssertionError("Expected false but got true");
        }
        ois.close();
    }

    private static void testReadBooleanEOF() {
        byte[] data = {};
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            ois.readBoolean();
            throw new AssertionError("Expected EOFException not thrown");
        } catch (EOFException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
