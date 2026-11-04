/*
 * @test
 * @summary Test ObjectInputStream.readLong() behavior
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadLongNormal();
        testReadLongWithEmptyStream();
    }

    private static void testReadLongNormal() throws IOException {
        // Prepare a byte array with a known long value (0x0102030405060708L)
        byte[] bytes = new byte[] {
            1, 2, 3, 4, 5, 6, 7, 8
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);

        long value = ois.readLong();

        // The expected value is 0x0102030405060708L
        long expected = 0x0102030405060708L;
        if (value != expected) {
            throw new AssertionError("readLong() returned " + value + ", expected " + expected);
        }

        ois.close();
    }

    private static void testReadLongWithEmptyStream() throws IOException {
        // An empty stream should cause EOFException when readLong is called
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            ois.readLong();
            throw new AssertionError("Expected IOException due to empty stream");
        } catch (IOException e) {
            // Expected exception
        } finally {
            ois.close();
        }
    }
}
