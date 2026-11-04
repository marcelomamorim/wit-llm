/*
 * @test
 * @summary Checks public behavior of CertificateIssuerName.get(String) for known and unknown attribute names
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;

public class CertificateIssuerNameWitupTest {

    public static void main(String[] args) throws Exception {
        testDnNameReturnsStoredNullName();
        testDnNameIsCaseInsensitive();
        testX500PrincipalReturnsNullWhenNoName();
        testUnknownNameThrowsIOException();
        testEmptyNameThrowsIOException();
        testIssuerNameIsNotRecognized();
    }

    private static void testDnNameReturnsStoredNullName() throws Exception {
        CertificateIssuerName cin = new CertificateIssuerName((X500Name) null);
        Object value = cin.get(CertificateIssuerName.DN_NAME);
        if (value != null) {
            throw new AssertionError("expected null for dname, got " + value);
        }
    }

    private static void testDnNameIsCaseInsensitive() throws Exception {
        CertificateIssuerName cin = new CertificateIssuerName((X500Name) null);
        Object lower = cin.get("dname");
        Object upper = cin.get("DNAME");
        if (lower != upper) {
            throw new AssertionError("dname lookup differs by case");
        }
    }

    private static void testX500PrincipalReturnsNullWhenNoName() throws Exception {
        CertificateIssuerName cin = new CertificateIssuerName((X500Name) null);
        Object value = cin.get(CertificateIssuerName.DN_PRINCIPAL);
        if (value != null) {
            throw new AssertionError("expected null for x500principal when no name, got " + value);
        }
    }

    private static void testUnknownNameThrowsIOException() throws Exception {
        CertificateIssuerName cin = new CertificateIssuerName((X500Name) null);
        try {
            cin.get("unknownAttribute");
            throw new AssertionError("expected IOException for unknown attribute");
        } catch (IOException expected) {
            // expected
        }
    }

    private static void testEmptyNameThrowsIOException() throws Exception {
        CertificateIssuerName cin = new CertificateIssuerName((X500Name) null);
        try {
            cin.get("");
            throw new AssertionError("expected IOException for empty attribute name");
        } catch (IOException expected) {
            // expected
        }
    }

    private static void testIssuerNameIsNotRecognized() throws Exception {
        CertificateIssuerName cin = new CertificateIssuerName((X500Name) null);
        try {
            cin.get(CertificateIssuerName.NAME);
            throw new AssertionError("expected IOException for sub-attribute name 'issuer'");
        } catch (IOException expected) {
            // expected
        }
    }
}
