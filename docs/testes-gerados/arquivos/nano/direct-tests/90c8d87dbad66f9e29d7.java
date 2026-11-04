/* @test    @summary Test the get method of CertificateIssuerExtension for valid and invalid attribute names    @run main CertificateIssuerExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.util.GeneralNames;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        GeneralNames dummyNames = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(dummyNames);
        GeneralNames result = ext.get("issuer");
        if (result != dummyNames) {
            throw new AssertionError("Expected the same GeneralNames instance");
        }
    }

    private static void testGetWithInvalidName() {
        GeneralNames dummyNames = new GeneralNames();
        try {
            CertificateIssuerExtension ext = new CertificateIssuerExtension(dummyNames);
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
