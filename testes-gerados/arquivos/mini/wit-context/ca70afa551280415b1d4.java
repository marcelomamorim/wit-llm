/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getSequence(int) for tag validation and exception paths
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testThrowsIOExceptionOnWrongTag();
        testThrowsIOExceptionOnReadVectorEOF();
    }

    private static void testThrowsIOExceptionOnWrongTag() throws Exception {
        // Prepare input with first byte != DerValue.tag_Sequence
        byte[] data = new byte[] { (byte)(DerValue.tag_Sequence + 1), 0x00 };
        DerInputStream derIn = new DerInputStream(data);
        try {
            derIn.getSequence(0);
            throw new RuntimeException("Expected IOException for wrong tag not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!"Sequence tag error".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }

    private static void testThrowsIOExceptionOnReadVectorEOF() throws Exception {
        // Prepare input with correct tag but no further data to cause EOF in readVector
        byte[] data = new byte[] { DerValue.tag_Sequence };
        DerInputStream derIn = new DerInputStream(data);
        try {
            derIn.getSequence(0);
            throw new RuntimeException("Expected IOException due to EOF not thrown");
        } catch (IOException e) {
            // IOException expected due to EOF or readVector failure
            // No specific message asserted as it depends on internal readVector implementation
        }
    }
}