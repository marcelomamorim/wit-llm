/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getSequence(int) method
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidSequence();
        testInvalidTagThrows();
    }

    private static void testValidSequence() throws IOException {
        // DER encoding of a sequence with one integer element (0x01)
        // Sequence tag (0x30), length 3, integer tag (0x02), length 1, value 0x01
        byte[] seqData = new byte[] { 0x30, 0x03, 0x02, 0x01, 0x01 };
        DerInputStream derIn = new DerInputStream(seqData);
        DerValue[] values = derIn.getSequence(3);
        if (values == null || values.length == 0) {
            throw new RuntimeException("Expected non-empty sequence");
        }
        // Check first DerValue tag is integer (0x02)
        if (values[0].tag != 0x02) {
            throw new RuntimeException("Expected integer tag in sequence element");
        }
    }

    private static void testInvalidTagThrows() {
        // Data with invalid tag (not sequence 0x30)
        byte[] invalidData = new byte[] { 0x31, 0x00 };
        try {
            DerInputStream derIn = new DerInputStream(invalidData);
            derIn.getSequence(0);
            throw new RuntimeException("Expected IOException for invalid sequence tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Sequence tag error")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
