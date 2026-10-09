/* @test
   @summary Testa o método get(String) de CertificateVersion para diferentes nomes.
   @run main CertificateVersionWitupTest_P80c574d6cfa6
 */
import java.io.IOException;
import sun.security.x509.CertificateVersion;
public class CertificateVersionWitupTest_P80c574d6cfa6 {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        CertificateVersion cv = new CertificateVersion();
        Integer v = cv.get("x509.info.version");
        if (v == null || v.intValue() != CertificateVersion.V1) {
            throw new AssertionError("Expected version V1 for valid name, got: " + v);
        }
    }

    private static void testGetWithInvalidName() {
        CertificateVersion cv = new CertificateVersion();
        try {
            cv.get("invalid.name");
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
