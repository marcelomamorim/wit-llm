/*
 * @test
 * @summary Witup-generated checks for CertificateVersion.delete(String)
 * @modules java.base/sun.security.x509
 * @run main CertificateVersionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V2);
        cv.delete("number");
        cv.delete("NUMBER");
        cv.delete("Number");

        expectIOException(new CertificateVersion(CertificateVersion.V3), "foo");
        expectIOException(new CertificateVersion(CertificateVersion.V1), "");
        expectIOException(new CertificateVersion(CertificateVersion.V2), "numbe");
        expectIOException(new CertificateVersion(CertificateVersion.V2), "version");

        boolean npe = false;
        try {
            new CertificateVersion(CertificateVersion.V2).delete(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("Expected NullPointerException for null name");
        }
    }

    private static void expectIOException(CertificateVersion cv, String name) {
        boolean thrown = false;
        try {
            cv.delete(name);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException for name: " + name);
        }
    }
}
