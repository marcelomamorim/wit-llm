/*
 * @test
 * @summary Test sun.security.x509.CertificateIssuerName.delete(String) method
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;

public class CertificateIssuerNameWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws IOException {
        X500Name x500Name = new X500Name(); // X500Name has public no-arg constructor in JDK 11
        CertificateIssuerName certIssuer = new CertificateIssuerName(x500Name);

        certIssuer.delete("dname");

        // After delete with "dname", toString should return empty string since dnName is null
        String result = certIssuer.toString();
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string after delete with 'dname', got: " + result);
        }
    }

    private static void testDeleteWithInvalidName() {
        try {
            X500Name x500Name = new X500Name();
            CertificateIssuerName certIssuer = new CertificateIssuerName(x500Name);

            certIssuer.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected IOException message: " + msg);
            }
        }
    }
}
