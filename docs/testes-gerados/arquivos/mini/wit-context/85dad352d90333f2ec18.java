/*
 * @test
 * @summary Test sun.security.x509.OIDName constructor with DerValue input
 * @run main OIDNameWitupTest
 */

import sun.security.x509.OIDName;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class OIDNameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidDerValue();
    }

    private static void testValidDerValue() throws IOException {
        // Create a DerValue with tag 6 (OBJECT IDENTIFIER) and valid OID bytes
        // OID 1.2.3.4 encoded in DER: 06 03 2A 03 04
        byte[] oidBytes = new byte[] { 0x06, 0x03, 0x2A, 0x03, 0x04 };
        DerValue derValue = new DerValue(oidBytes);

        OIDName oidName = new OIDName(derValue);
        ObjectIdentifier oid = oidName.getOID();

        // The OID string should be "1.2.3.4"
        String oidStr = oid.toString();
        if (!"1.2.3.4".equals(oidStr)) {
            throw new RuntimeException("Expected OID string '1.2.3.4' but got: " + oidStr);
        }

        // The toString method of OIDName should include the OID string
        String toString = oidName.toString();
        if (!toString.contains("1.2.3.4")) {
            throw new RuntimeException("OIDName.toString() does not contain expected OID string: " + toString);
        }
    }

    private static void testInvalidDerValue() {
        // Create a DerValue with invalid tag (not 6) to trigger IOException
        // Tag 5 (NULL) with empty content
        byte[] invalidBytes = new byte[] { 0x05, 0x00 };
        try {
            DerValue derValue = new DerValue(invalidBytes);
            // Attempt to create OIDName should throw IOException because getOID() expects tag 6
            new OIDName(derValue);
            throw new RuntimeException("Expected IOException not thrown for invalid DerValue tag");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
