/*
 * @test
 * @summary Test DerInputStream constructor with offset and length
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with valid offset and length
        byte[] data = new byte[] {0x01, 0x02, 0x03, 0x04, 0x05};
        DerInputStream dis = new DerInputStream(data, 1, 3);
        if (dis == null) {
            throw new RuntimeException("DerInputStream constructor returned null");
        }

        // Test with offset = 0 and length = data.length
        DerInputStream dis2 = new DerInputStream(data, 0, data.length);
        if (dis2 == null) {
            throw new RuntimeException("DerInputStream constructor returned null");
        }

        // Test with offset + length == data.length
        DerInputStream dis3 = new DerInputStream(data, 2, 3);
        if (dis3 == null) {
            throw new RuntimeException("DerInputStream constructor returned null");
        }

        // Test with zero length
        DerInputStream dis4 = new DerInputStream(data, 2, 0);
        if (dis4 == null) {
            throw new RuntimeException("DerInputStream constructor returned null");
        }

        // Test with offset == data.length and length == 0 (empty slice at end)
        DerInputStream dis5 = new DerInputStream(data, data.length, 0);
        if (dis5 == null) {
            throw new RuntimeException("DerInputStream constructor returned null");
        }

        // Test with invalid offset (negative) - expect IOException
        try {
            new DerInputStream(data, -1, 2);
            throw new RuntimeException("Expected IOException for negative offset");
        } catch (IOException e) {
            // expected
        }

        // Test with invalid length (negative) - expect IOException
        try {
            new DerInputStream(data, 1, -1);
            throw new RuntimeException("Expected IOException for negative length");
        } catch (IOException e) {
            // expected
        }

        // Test with offset + length > data.length - expect IOException
        try {
            new DerInputStream(data, 3, 3);
            throw new RuntimeException("Expected IOException for offset + length > data.length");
        } catch (IOException e) {
            // expected
        }
    }
}
