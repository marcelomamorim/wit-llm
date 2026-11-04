/*
 * @test
 * @summary Test BufferedInputStream constructor with default buffer size
 * @run main BufferedInputStreamWitupTest
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BufferedInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        // Test with a normal ByteArrayInputStream
        byte[] data = {1, 2, 3, 4, 5};
        InputStream bais = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(bais);

        // Read all bytes and verify content
        for (int i = 0; i < data.length; i++) {
            int readByte = bis.read();
            if (readByte != (data[i] & 0xFF)) {
                throw new RuntimeException("Byte mismatch at index " + i + ": expected " + data[i] + ", got " + readByte);
            }
        }

        // After reading all bytes, read() should return -1
        if (bis.read() != -1) {
            throw new RuntimeException("Expected end of stream (-1) after reading all bytes");
        }

        bis.close();

        // Test with null InputStream should throw NullPointerException
        try {
            new BufferedInputStream(null);
            throw new RuntimeException("Expected NullPointerException when passing null InputStream");
        } catch (NullPointerException expected) {
            // Expected
        }
    }
}
