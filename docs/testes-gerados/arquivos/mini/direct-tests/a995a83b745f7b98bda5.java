/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getDerValue() basic behavior
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testEmptyInput();
    }

    private static void testValidDerValue() throws IOException {
        // A minimal valid DER encoding for an INTEGER 0 (tag 0x02, length 1, value 0)
        byte[] derEncodedIntZero = new byte[] {0x02, 0x01, 0x00};
        DerInputStream dis = new DerInputStream(derEncodedIntZero);
        DerValue val = dis.getDerValue();
        if (val == null) {
            throw new RuntimeException("getDerValue() returned null for valid DER data");
        }
        // Check tag is INTEGER (0x02)
        if (val.tag != 0x02) {
            throw new RuntimeException("Expected tag 0x02 but got " + val.tag);
        }
    }

    private static void testEmptyInput() {
        // Empty input should cause IOException when getDerValue() is called
        byte[] empty = new byte[0];
        try {
            DerInputStream dis = new DerInputStream(empty);
            dis.getDerValue();
            throw new RuntimeException("Expected IOException for empty input but none thrown");
        } catch (IOException e) {
            // Expected
        }
    }
}
