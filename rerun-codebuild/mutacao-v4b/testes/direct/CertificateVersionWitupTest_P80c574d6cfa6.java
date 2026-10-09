/* @test
   @summary Tests the get(String name) method of CertificateVersion.
   @run main CertificateVersionWitupTest_P80c574d6cfa6
 */
import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest_P80c574d6cfa6 {
    public static void main(String[] args) throws Exception {
        testGetVersion();
        testGetInvalidName();
    }

    private static void testGetVersion() throws IOException {
        CertificateVersion cv = new CertificateVersion(2);
        Integer result = cv.get("version");
        if (result == null || result.intValue() != 2) {
            throw new AssertionError("Expected version 2, got " + result);
        }
    }

    private static void testGetInvalidName() {
        try {
            CertificateVersion cv = new CertificateVersion(1);
            cv.get("invalidName");
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            // expected
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
