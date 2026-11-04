/*
 * @test
 * @summary Test sun.security.util.DerValue.getBoolean() for correct boolean extraction and exception throwing
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import java.io.IOException;
import java.io.ByteArrayInputStream;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testGetBooleanValidTrue();
        testGetBooleanValidFalse();
        testGetBooleanInvalidTag();
        testGetBooleanInvalidLength();
    }

    private static void testGetBooleanValidTrue() throws Exception {
        // Construct DER encoding for BOOLEAN true: tag=0x01, length=1, value=0xFF (non-zero means true)
        byte[] derTrue = new byte[] { 0x01, 0x01, (byte)0xFF };
        DerValue dv = new DerValue(derTrue);
        boolean val = dv.getBoolean();
        if (!val) {
            throw new RuntimeException("Expected true boolean value but got false");
        }
    }

    private static void testGetBooleanValidFalse() throws Exception {
        // Construct DER encoding for BOOLEAN false: tag=0x01, length=1, value=0x00
        byte[] derFalse = new byte[] { 0x01, 0x01, 0x00 };
        DerValue dv = new DerValue(derFalse);
        boolean val = dv.getBoolean();
        if (val) {
            throw new RuntimeException("Expected false boolean value but got true");
        }
    }

    private static void testGetBooleanInvalidTag() throws Exception {
        // Construct DER encoding with invalid tag (e.g. INTEGER 0x02) but length=1 and value=0x00
        byte[] derInvalidTag = new byte[] { 0x02, 0x01, 0x00 };
        DerValue dv = new DerValue(derInvalidTag);
        try {
            dv.getBoolean();
            throw new RuntimeException("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            // Expected
            String msg = e.getMessage();
            if (!msg.contains("not a BOOLEAN")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }

    private static void testGetBooleanInvalidLength() throws Exception {
        // Construct DER encoding with valid BOOLEAN tag but invalid length (e.g. length=2)
        byte[] derInvalidLength = new byte[] { 0x01, 0x02, 0x00, 0x00 };
        DerValue dv = new DerValue(derInvalidLength);
        try {
            dv.getBoolean();
            throw new RuntimeException("Expected IOException for invalid length but none thrown");
        } catch (IOException e) {
            // Expected
            String msg = e.getMessage();
            if (!msg.contains("invalid length")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
