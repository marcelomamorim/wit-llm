/* @test    @summary Test DNSName constructor with valid DerValue and invalid tag to trigger IOException    @run main DNSNameWitupTest */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.DNSName;

public class DNSNameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidDerValue();
        testInvalidTagThrowsIOException();
    }

    private static void testValidDerValue() throws Exception {
        // Create a DerValue with a valid IA5String (e.g., "example.com")
        DerValue validDerValue = new DerValue("example.com".getBytes("UTF-8"));
        // Wrap in DerValue as IA5String
        DerValue derValue = new DerValue(validDerValue.toByteArray());
        // Instantiate DNSName with valid DerValue
        DNSName dnsName = new DNSName(derValue);
        // Verify that the name is correctly set
        if (!"example.com".equals(dnsName.name)) {
            throw new AssertionError("Expected name to be 'example.com', but was: " + dnsName.name);
        }
    }

    private static void testInvalidTagThrowsIOException() {
        // Create a DerValue with an invalid tag (simulate tag != 22)
        // Since DerValue constructor with byte[] does not allow setting tag directly,
        // we simulate by creating a DerValue with a different tag.
        // For simplicity, create a DerValue with an invalid tag byte.
        byte[] invalidBytes = { (byte)0x1F, 'x', 'y', 'z' }; // tag 31 (0x1F)
        try {
            DerValue invalidDerValue = new DerValue(invalidBytes);
            // Attempt to create DNSName, expecting IOException
            new DNSName(invalidDerValue);
            throw new AssertionError("Expected IOException was not thrown for invalid tag");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("IA5String")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
