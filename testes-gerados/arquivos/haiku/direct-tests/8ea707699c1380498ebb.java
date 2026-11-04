/*
 * @test
 * @summary Verifies CertificateIssuerName.delete(String) clears the DN name for "dname" (case-insensitive) and rejects other attribute names.
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;

public class CertificateIssuerNameWitupTest {
    public static void main(String[] args) throws Exception {
        X500Name name = new X500Name("CN=Test, O=Example, C=US");

        CertificateIssuerName cin = new CertificateIssuerName(name);
        String before = cin.toString();
        if (!before.equals(name.toString())) {
            throw new RuntimeException("Unexpected toString before delete: " + before);
        }
        cin.delete("dname");
        if (!cin.toString().isEmpty()) {
            throw new RuntimeException("Expected empty string after delete(\"dname\")");
        }

        CertificateIssuerName upper = new CertificateIssuerName(name);
        upper.delete("DNAME");
        if (!upper.toString().isEmpty()) {
            throw new RuntimeException("Expected empty string after delete(\"DNAME\")");
        }

        CertificateIssuerName other = new CertificateIssuerName(name);
        try {
            other.delete("issuer");
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            // expected
        }
        if (!other.toString().equals(name.toString())) {
            throw new RuntimeException("Name must be unchanged after rejected delete");
        }

        try {
            other.delete(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
