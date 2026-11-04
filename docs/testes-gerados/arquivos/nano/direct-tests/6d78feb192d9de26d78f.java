/* @test    @summary Test constructor CertificateVersion(DerInputStream in) for sun.security.x509.CertificateVersion    @run main CertificateVersionWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws IOException {
        testValidDerInput();
        testInvalidDerInput();
    }

    private static void testValidDerInput() throws IOException {
        // Create a DerValue representing version 2 (integer 2)
        byte[] derBytes = {0x02, 0x01, 0x02}; // Tag=0x02 (INTEGER), length=1, value=0x02
        DerValue derValue = new DerValue(derBytes);
        DerInputStream derIn = new DerInputStream(new ByteArrayInputStream(derBytes));
        // Wrap DerValue in DerInputStream
        DerInputStream derStream = new DerInputStream(new ByteArrayInputStream(derBytes));
        // Instantiate CertificateVersion with valid DerInputStream
        CertificateVersion cv = new CertificateVersion(derStream);
        // Check that version is 2
        if (cv.getVersion() != 2) {
            throw new AssertionError("Expected version 2, got " + cv.getVersion());
        }
    }

    private static void testInvalidDerInput() {
        // Create a DerValue with invalid format (empty data)
        byte[] invalidBytes = {};
        try {
            DerInputStream derIn = new DerInputStream(new ByteArrayInputStream(invalidBytes));
            new CertificateVersion(derIn);
            throw new AssertionError("Expected IOException for invalid DER input");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
