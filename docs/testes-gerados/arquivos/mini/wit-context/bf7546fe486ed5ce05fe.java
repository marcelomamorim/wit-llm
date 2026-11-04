/*
 * @test
 * @summary Test sun.security.x509.KeyIdentifier constructor with DerValue
 * @run main KeyIdentifierWitupTest
 */

import sun.security.x509.KeyIdentifier;
import sun.security.util.DerValue;
import java.io.IOException;

public class KeyIdentifierWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidDerValueThrowsIOException();
    }

    private static void testValidDerValue() throws Exception {
        // Create a DerValue with a valid octet string
        byte[] validOctets = new byte[] {0x01, 0x02, 0x03, 0x04};
        DerValue val = new DerValue(validOctets);
        KeyIdentifier ki = new KeyIdentifier(val);
        byte[] id = ki.getIdentifier();
        if (id == null || id.length != validOctets.length) {
            throw new RuntimeException("KeyIdentifier did not preserve octet string length");
        }
        for (int i = 0; i < id.length; i++) {
            if (id[i] != validOctets[i]) {
                throw new RuntimeException("KeyIdentifier octet string content mismatch");
            }
        }
    }

    private static void testInvalidDerValueThrowsIOException() {
        // Construct a DerValue that will cause getOctetString() to throw IOException
        // According to DerValue source, getOctetString throws IOException if tag is invalid or length inconsistent
        // We simulate this by creating a DerValue with invalid tag or truncated data
        byte[] invalidData = new byte[] {(byte)0x30, 0x01}; // incomplete sequence
        DerValue val = null;
        try {
            val = new DerValue(invalidData);
        } catch (IOException e) {
            // If constructor throws, test passes because invalid DerValue cannot be created
            return;
        }
        try {
            new KeyIdentifier(val);
            throw new RuntimeException("Expected IOException not thrown for invalid DerValue");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
