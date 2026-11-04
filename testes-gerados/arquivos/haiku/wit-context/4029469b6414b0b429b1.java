/*
 * @test
 * @summary Verifies CertificateIssuerExtension.set rejects unrecognized names and values of the wrong type with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerExtensionWitupTest
 */
import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.GeneralNames;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateIssuerExtension ext = new CertificateIssuerExtension((GeneralNames) null);

        expectIOException(ext, "unknownName", null);
        expectIOException(ext, "unknownName", "value");
        expectIOException(ext, "", null);
        expectIOException(ext, "issuerX", null);
        expectIOException(ext, "issuer", "notGeneralNames");
        expectIOException(ext, "ISSUER", Integer.valueOf(1));
        expectIOException(ext, "Issuer", null);
    }

    private static void expectIOException(CertificateIssuerExtension ext, String name, Object value) {
        try {
            ext.set(name, value);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name + " value=" + value);
    }
}
