/*
 * @test
 * @summary Behavioral tests for CertificateSubjectName.get(String) covering dname, x500principal, unknown names and null names
 * @modules java.base/sun.security.x509
 * @run main CertificateSubjectNameWitupTest
 */

import java.io.IOException;
import javax.security.auth.x500.X500Principal;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.X500Name;

public class CertificateSubjectNameWitupTest {

    public static void main(String[] args) throws Exception {
        testDnameReturnsSameName();
        testDnameIsCaseInsensitive();
        testPrincipalMatchesName();
        testPrincipalIsCached();
        testUnrecognizedNameThrows();
        testNullNameReturnsNull();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testDnameReturnsSameName() throws Exception {
        X500Name name = new X500Name("CN=Test, O=Example, C=US");
        CertificateSubjectName csn = new CertificateSubjectName(name);
        Object got = csn.get("dname");
        check(got == name, "get(dname) should return the same X500Name instance");
    }

    static void testDnameIsCaseInsensitive() throws Exception {
        X500Name name = new X500Name("CN=Case, O=Example, C=US");
        CertificateSubjectName csn = new CertificateSubjectName(name);
        check(csn.get("DNAME") == name, "get(DNAME) should match dname ignoring case");
        check(csn.get("DName") == name, "get(DName) should match dname ignoring case");
    }

    static void testPrincipalMatchesName() throws Exception {
        X500Name name = new X500Name("CN=Principal, O=Example, C=US");
        CertificateSubjectName csn = new CertificateSubjectName(name);
        Object got = csn.get("x500principal");
        check(got instanceof X500Principal, "get(x500principal) should return X500Principal");
        X500Principal expected = name.asX500Principal();
        check(expected.equals(got), "get(x500principal) should equal name.asX500Principal()");
    }

    static void testPrincipalIsCached() throws Exception {
        X500Name name = new X500Name("CN=Cached, O=Example, C=US");
        CertificateSubjectName csn = new CertificateSubjectName(name);
        Object first = csn.get("x500principal");
        Object second = csn.get("X500PRINCIPAL");
        check(first != null, "first x500principal result should not be null");
        check(first == second, "repeated x500principal lookups should return the cached instance");
    }

    static void testUnrecognizedNameThrows() throws Exception {
        CertificateSubjectName csn =
            new CertificateSubjectName(new X500Name("CN=Unknown, O=Example, C=US"));
        boolean thrown = false;
        try {
            csn.get("unknown");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "get(unknown) should throw IOException");

        thrown = false;
        try {
            csn.get("subject");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "get(subject) should throw IOException since it is not handled by get");
    }

    static void testNullNameReturnsNull() throws Exception {
        CertificateSubjectName csn = new CertificateSubjectName((X500Name) null);
        check(csn.get("dname") == null, "get(dname) should be null for null name");
        check(csn.get("x500principal") == null, "get(x500principal) should be null for null name");
    }
}
