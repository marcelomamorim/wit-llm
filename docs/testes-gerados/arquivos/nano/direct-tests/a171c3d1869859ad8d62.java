/* @test    @summary Test the get method of CertificateVersion class for valid and invalid attribute names    @run main CertificateVersionWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        CertificateVersion version = new CertificateVersion();
        Integer result = version.get("version");
        if (result == null) {
            throw new AssertionError("Expected non-null result for valid attribute name");
        }
        if (result != 0 && result != 1 && result != 2) {
            throw new AssertionError("Unexpected version value: " + result);
        }
        System.out.println("testGetWithValidName passed.");
    }

    private static void testGetWithInvalidName() {
        CertificateVersion version = new CertificateVersion();
        try {
            version.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
            System.out.println("testGetWithInvalidName passed.");
        }
    }
}
