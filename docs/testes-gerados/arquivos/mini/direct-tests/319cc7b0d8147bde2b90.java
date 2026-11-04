/*
 * @test
 * @summary Test DerInputStream constructor with byte array input
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with empty byte array
        byte[] empty = new byte[0];
        DerInputStream disEmpty = new DerInputStream(empty);
        if (disEmpty == null) {
            throw new RuntimeException("DerInputStream constructor returned null for empty array");
        }

        // Test with non-empty byte array
        byte[] data = new byte[] {0x30, 0x03, 0x02, 0x01, 0x05}; // valid DER encoding for INTEGER 5
        DerInputStream disData = new DerInputStream(data);
        if (disData == null) {
            throw new RuntimeException("DerInputStream constructor returned null for valid data");
        }

        // Test with large byte array
        byte[] large = new byte[1024];
        for (int i = 0; i < large.length; i++) {
            large[i] = (byte)(i & 0xFF);
        }
        DerInputStream disLarge = new DerInputStream(large);
        if (disLarge == null) {
            throw new RuntimeException("DerInputStream constructor returned null for large data");
        }
    }
}
