/*
 * @test
 * @summary Witup-generated checks for CertificateIssuerName.delete(String)
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.IOException;

import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;

public class CertificateIssuerNameWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteDnNameClearsName();
        testDeleteDnNameIsCaseInsensitive();
        testDeleteUnknownNameThrowsIOException();
        testDeleteEmptyNameThrowsIOException();
        testDeleteIdentNameThrowsIOException();
    }

    private static void testDeleteDnNameClearsName() throws Exception {
        X500Name n = new X500Name("CN=Test, O=Example, C=US");
        CertificateIssuerName cin = new CertificateIssuerName(n);
        if (cin.toString().isEmpty()) {
            throw new RuntimeException("precondition: toString should be non-empty");
        }
        cin.delete("dname");
        if (!cin.toString().isEmpty()) {
            throw new RuntimeException("delete(dname) should clear the name");
        }
    }

    private static void testDeleteDnNameIsCaseInsensitive() throws Exception {
        X500Name n = new X500Name("CN=Test, O=Example, C=US");
        CertificateIssuerName cin = new CertificateIssuerName(n);
        cin.delete("DNAME");
        if (!cin.toString().isEmpty()) {
            throw new RuntimeException("delete(DNAME) should clear the name");
        }
    }

    private static void testDeleteUnknownNameThrowsIOException() throws Exception {
        X500Name n = new X500Name("CN=Test, O=Example, C=US");
        CertificateIssuerName cin = new CertificateIssuerName(n);
        try {
            cin.delete("issuer");
            throw new RuntimeException("expected IOException for unknown attribute");
        } catch (IOException expected) {
            // expected
        }
    }

    private static void testDeleteEmptyNameThrowsIOException() throws Exception {
        X500Name n = new X500Name("CN=Test, O=Example, C=US");
        CertificateIssuerName cin = new CertificateIssuerName(n);
        try {
            cin.delete("");
            throw new RuntimeException("expected IOException for empty attribute name");
        } catch (IOException expected) {
            // expected
        }
    }

    private static void testDeleteIdentNameThrowsIOException() throws Exception {
        X500Name n = new X500Name("CN=Test, O=Example, C=US");
        CertificateIssuerName cin = new CertificateIssuerName(n);
        try {
            cin.delete(CertificateIssuerName.IDENT);
            throw new RuntimeException("expected IOException for IDENT attribute name");
        } catch (IOException expected) {
            // expected
        }
    }
}
