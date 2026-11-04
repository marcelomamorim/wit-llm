/*
 * @test
 * @summary Test CertificateIssuerExtension.get(String) method behavior
 * @run main CertificateIssuerExtensionWitupTest
 */

import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralName;
import sun.security.x509.DNSName;
import java.io.IOException;

public class CertificateIssuerExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithIssuerName();
        testGetWithInvalidNameThrowsIOException();
    }

    private static void testGetWithIssuerName() throws Exception {
        // Setup GeneralNames with one DNSName
        GeneralName gn = new DNSName("example.com");
        GeneralNames gns = new GeneralNames();
        gns.add(gn);

        CertificateIssuerExtension ext = new CertificateIssuerExtension(gns);

        GeneralNames result = ext.get("issuer");
        if (result == null) {
            throw new RuntimeException("get(\"issuer\") returned null, expected non-null");
        }
        if (!result.equals(gns)) {
            throw new RuntimeException("get(\"issuer\") did not return the expected GeneralNames instance");
        }
    }

    private static void testGetWithInvalidNameThrowsIOException() {
        try {
            GeneralNames gns = new GeneralNames();
            CertificateIssuerExtension ext = new CertificateIssuerExtension(gns);
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
            // Expected exception caught, test passes
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type thrown: " + e);
        }
    }
}
