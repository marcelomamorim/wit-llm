/*
 * @test
 * @summary Deterministic tests for sun.security.util.IOUtils.readNBytes
 * @run main IOUtilsWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import sun.security.util.IOUtils;

public class IOUtilsWitupTest {
    public static void main(String[] args) throws Exception {
        testNegativeLengthThrows();
        testReadExactBytes();
        testReadLessThanAvailableThrowsEOF();
        testReadMoreThanAvailableThrowsEOF();
    }

    private static void testNegativeLengthThrows() {
        InputStream is = new ByteArrayInputStream(new byte[] {1, 2, 3});
        try {
            IOUtils.readNBytes(is, -1);
            throw new RuntimeException("Expected IOException for negative length");
        } catch (IOException e) {
            if (!e.getMessage().contains("length cannot be negative")) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    private static void testReadExactBytes() throws IOException {
        byte[] data = new byte[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        InputStream is = new ByteArrayInputStream(data);
        byte[] result = IOUtils.readNBytes(is, 10);
        if (result.length != 10) {
            throw new RuntimeException("Expected 10 bytes, got " + result.length);
        }
        for (int i = 0; i < 10; i++) {
            if (result[i] != (byte) i) {
                throw new RuntimeException("Byte mismatch at index " + i);
            }
        }
    }

    private static void testReadLessThanAvailableThrowsEOF() {
        byte[] data = new byte[5];
        InputStream is = new ByteArrayInputStream(data);
        try {
            IOUtils.readNBytes(is, 10);
            throw new RuntimeException("Expected EOFException due to insufficient bytes");
        } catch (EOFException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Expected EOFException but got " + e);
        }
    }

    private static void testReadMoreThanAvailableThrowsEOF() {
        byte[] data = new byte[0];
        InputStream is = new ByteArrayInputStream(data);
        try {
            IOUtils.readNBytes(is, 1);
            throw new RuntimeException("Expected EOFException due to no bytes available");
        } catch (EOFException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Expected EOFException but got " + e);
        }
    }
}
