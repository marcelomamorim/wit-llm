/*
 * @test
 * @summary Test deterministic behavior of ObjectInputStream.readLong()
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testReadLongNormal();
        testReadLongBlockModeFallback();
    }

    private static void testReadLongNormal() throws IOException {
        // Prepare a byte array with 8 bytes representing a long value
        byte[] longBytes = new byte[] {
            0, 0, 0, 0, 0, 0, 0, 1
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(longBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);

        long value = ois.readLong();

        if (value != 1L) {
            throw new AssertionError("Expected 1L but got " + value);
        }

        ois.close();
    }

    private static void testReadLongBlockModeFallback() throws IOException {
        // This test attempts to trigger the else if branch where (end - pos) < 8
        // Since the internal fields are not accessible, we simulate by providing
        // an InputStream that returns fewer than 8 bytes to cause fallback to din.readLong()

        // Provide an InputStream with fewer than 8 bytes to cause readFully to fail
        byte[] shortBytes = new byte[] {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(shortBytes);

        ObjectInputStream ois = new ObjectInputStream(bais) {
            // Override to simulate block mode true and end - pos < 8
            // We cannot access internal fields, so we rely on the fallback behavior
            // The readLong() call will attempt to read 8 bytes and fail with EOFException
        };

        boolean threwEOF = false;
        try {
            ois.readLong();
        } catch (IOException e) {
            // Expecting IOException due to insufficient bytes
            threwEOF = true;
        } finally {
            ois.close();
        }

        if (!threwEOF) {
            throw new AssertionError("Expected IOException due to insufficient bytes");
        }
    }
}
