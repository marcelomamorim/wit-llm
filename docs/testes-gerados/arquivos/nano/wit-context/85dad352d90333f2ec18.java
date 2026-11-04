/* @test    @summary Test for sun.security.x509.OIDName constructor with DerValue input    @run main OIDNameWitupTest */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidDerValue();
        testConstructorWithInvalidDerValue();
    }

    private static void testConstructorWithValidDerValue() throws Exception {
        // Create a DerValue with a valid OID tag (tag=6)
        byte[] oidBytes = {0x06, 0x03, 0x2A, 0x03, 0x04}; // OID: 1.2.3.4
        DerValue derValue = new DerValue(oidBytes);
        // The constructor should succeed without exception
        OIDName oidName = new OIDName(derValue);
        assert "OIDName: 1.2.3.4".equals(oidName.toString()) : "Unexpected toString() output";
        assert oidName.getOID().toString().equals("1.2.3.4") : "OID mismatch";
    }

    private static void testConstructorWithInvalidDerValue() {
        // Create a DerValue with a non-OID tag (e.g., tag=5, NULL)
        byte[] nullBytes = {0x05, 0x00}; // NULL
        DerValue derValue = null;
        try {
            derValue = new DerValue(nullBytes);
        } catch (IOException e) {
            // Should not happen, as bytes are valid DER for NULL
            throw new RuntimeException("Failed to create DerValue for NULL", e);
        }
        // Attempt to create OIDName with invalid DerValue; expect IOException
        try {
            new OIDName(derValue);
            assert false : "Expected IOException was not thrown";
        } catch (IOException e) {
            // Expected exception
            assert e.getMessage().contains("getOID") : "Unexpected exception message";
        }
    }
}
