/* @test    @summary Test the get(String name) method of CertificateVersion for recognized and unrecognized attribute names.    @run main CertificateVersionWitupTest_Pefcfa9729c3f */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest_Pefcfa9729c3f {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        CertificateVersion version = new CertificateVersion(2);
        Integer result = version.get("version");
        if (result == null || result != 2) {
            throw new AssertionError("Expected version 2, but got: " + result);
        }
        Integer resultCaseInsensitive = version.get("VErSioN");
        if (resultCaseInsensitive == null || resultCaseInsensitive != 2) {
            throw new AssertionError("Expected version 2 (case insensitive), but got: " + resultCaseInsensitive);
        }
    }

    private static void testGetWithInvalidName() {
        CertificateVersion version;
        try {
            version = new CertificateVersion(1);
            version.get("unknown");
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
