/* @test    @summary Test DerInputStream.getSequence method for correct sequence parsing and exception handling    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testValidSequence();
        testInvalidTagThrows();
    }

    private static void testValidSequence() throws IOException {
        byte[] validData = new byte[] {
            0x30, 0x03, 0x02, 0x01, 0x05
        };
        DerInputStream dis = new DerInputStream(validData);
        DerValue[] sequence = dis.getSequence(0);
        if (sequence.length != 1) {
            throw new AssertionError("Expected sequence length 1, got " + sequence.length);
        }
        if (sequence[0].tag != DerValue.tag_Sequence) {
            throw new AssertionError("Sequence tag mismatch");
        }
    }

    private static void testInvalidTagThrows() {
        byte[] invalidData = new byte[] {
            0x31, 0x03, 0x02, 0x01, 0x05
        };
        try {
            DerInputStream dis = new DerInputStream(invalidData);
            dis.getSequence(0);
            throw new AssertionError("Expected IOException for invalid tag");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
