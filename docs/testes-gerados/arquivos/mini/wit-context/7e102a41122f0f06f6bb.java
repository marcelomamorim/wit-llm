/*
 * @test
 * @summary Test ObjectInputStream.readBoolean() for EOFException and normal behavior
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadBooleanNormal();
        testReadBooleanEOFException();
    }

    private static void testReadBooleanNormal() throws IOException {
        // Input stream with one byte 0x01 (true)
        byte[] dataTrue = new byte[] {1};
        ObjectInputStream oisTrue = new ObjectInputStream(new ByteArrayInputStream(dataTrue));
        boolean valTrue = oisTrue.readBoolean();
        if (valTrue != true) {
            throw new AssertionError("Expected true but got false");
        }
        oisTrue.close();

        // Input stream with one byte 0x00 (false)
        byte[] dataFalse = new byte[] {0};
        ObjectInputStream oisFalse = new ObjectInputStream(new ByteArrayInputStream(dataFalse));
        boolean valFalse = oisFalse.readBoolean();
        if (valFalse != false) {
            throw new AssertionError("Expected false but got true");
        }
        oisFalse.close();
    }

    private static void testReadBooleanEOFException() throws IOException {
        // Empty input stream to cause EOFException on readBoolean
        byte[] empty = new byte[0];
        ObjectInputStream oisEmpty = new ObjectInputStream(new ByteArrayInputStream(empty));
        try {
            oisEmpty.readBoolean();
            throw new AssertionError("Expected EOFException not thrown");
        } catch (EOFException e) {
            // Expected exception
        } finally {
            oisEmpty.close();
        }
    }
}
