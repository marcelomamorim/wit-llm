/*
 * @test
 * @summary Verify CertificateIssuerName.get(String) returns null for the dname and x500principal accessors when the name is null, and throws IOException for unrecognized attribute names
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.IOException;

import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;

public class CertificateIssuerNameWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateIssuerName cin = new CertificateIssuerName((X500Name) null);

        check(cin.get("dname") == null, "dname should return null for null name");
        check(cin.get("DNAME") == null, "DNAME should be case-insensitive");
        check(cin.get("DName") == null, "DName should be case-insensitive");
        check(cin.get("x500principal") == null,
              "x500principal should return null for null name");
        check(cin.get("X500PRINCIPAL") == null,
              "X500PRINCIPAL should be case-insensitive");

        expectIOException(cin, "issuer");
        expectIOException(cin, "x509");
        expectIOException(cin, "");
        expectIOException(cin, "unknown");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectIOException(CertificateIssuerName cin, String name) {
        try {
            cin.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: " + name);
    }
}
