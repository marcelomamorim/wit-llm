/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getOID() for expected IOException and normal behavior
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidOID();
        testInvalidOIDTag();
    }

    private static void testValidOID() throws IOException {
        // Valid DER encoding for OID tag (0x06) with minimal content
        // Example OID: 1.2.3 encoded in DER is 06 03 2A 03 04 (simplified)
        // We'll use a minimal valid encoding: tag 0x06, length 1, value 0x2A (1*40 + 2 = 42 decimal = 0x2A)
        byte[] validOidData = new byte[] { 0x06, 0x01, 0x2A };
        DerInputStream der = new DerInputStream(validOidData);
        ObjectIdentifier oid = der.getOID();
        if (oid == null) {
            throw new RuntimeException("getOID() returned null for valid input");
        }
    }

    private static void testInvalidOIDTag() {
        // Data with invalid tag (not 0x06) should cause IOException when getOID() is called
        // Using tag 0x05 (NULL) instead of 0x06
        byte[] invalidTagData = new byte[] { 0x05, 0x00 };
        try {
            DerInputStream der = new DerInputStream(invalidTagData);
            der.getOID();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
