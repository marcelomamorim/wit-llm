/*
 * @test
 * @summary Test sun.security.util.IOUtils.readNBytes method
 * @run main IOUtilsWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import sun.security.util.IOUtils;

public class IOUtilsWitupTest {
    public static void main(String[] args) throws Exception {
        testReadNBytesValidLength();
        testReadNBytesZeroLength();
        testReadNBytesNegativeLength();
        testReadNBytesPrematureEOF();
    }

    private static void testReadNBytesValidLength() throws IOException {
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        byte[] result = IOUtils.readNBytes(bais, 3);
        if (result.length != 3) {
            throw new RuntimeException("Expected length 3 but got " + result.length);
        }
        if (result[0] != 1 || result[1] != 2 || result[2] != 3) {
            throw new RuntimeException("Data mismatch in readNBytes");
        }
    }

    private static void testReadNBytesZeroLength() throws IOException {
        byte[] data = new byte[] {1, 2, 3};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        byte[] result = IOUtils.readNBytes(bais, 0);
        if (result.length != 0) {
            throw new RuntimeException("Expected zero length array but got " + result.length);
        }
    }

    private static void testReadNBytesNegativeLength() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        try {
            IOUtils.readNBytes(bais, -1);
            throw new RuntimeException("Expected IOException for negative length");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("length cannot be negative")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testReadNBytesPrematureEOF() {
        byte[] data = new byte[] {1, 2};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            IOUtils.readNBytes(bais, 5);
            throw new RuntimeException("Expected EOFException for premature EOF");
        } catch (EOFException e) {
            // Expected
        } catch (IOException e) {
            throw new RuntimeException("Expected EOFException but got " + e);
        }
    }
}
