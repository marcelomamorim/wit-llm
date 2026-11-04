/*
 * @test
 * @summary Checks CertificateSubjectName.get(String) lookup, case handling and unrecognized attribute exceptions
 * @modules java.base/sun.security.x509
 * @run main CertificateSubjectNameWitupTest
 */

import java.io.IOException;

import sun.security.x509.CertificateSubjectName;
import sun.security.x509.X500Name;

public class CertificateSubjectNameWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testUnrecognizedNameThrowsIOException() {
        CertificateSubjectName csn = new CertificateSubjectName((X500Name) null);
        boolean thrown = false;
        try {
            csn.get("unknown-attribute");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "get with unrecognized name must throw IOException");
    }

    private static void testNameConstantNotRecognized() {
        CertificateSubjectName csn = new CertificateSubjectName((X500Name) null);
        boolean thrown = false;
        try {
            csn.get(CertificateSubjectName.NAME);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "get(NAME) must throw IOException since it is not a supported accessor");
    }

    private static void testDnNameAccessorIsCaseInsensitive() throws IOException {
        CertificateSubjectName csn = new CertificateSubjectName((X500Name) null);
        Object lower = csn.get(CertificateSubjectName.DN_NAME);
        Object upper = csn.get("DNAME");
        check(lower == null, "dname with null X500Name must return null");
        check(upper == null, "DNAME with null X500Name must return null");
    }

    private static void testPrincipalAccessorWithNullNameReturnsNull() throws IOException {
        CertificateSubjectName csn = new CertificateSubjectName((X500Name) null);
        Object principal = csn.get(CertificateSubjectName.DN_PRINCIPAL);
        check(principal == null, "x500principal with null X500Name must return null");
        Object principalUpper = csn.get("X500PRINCIPAL");
        check(principalUpper == null, "X500PRINCIPAL with null X500Name must return null");
    }

    private static void testNullAttributeNameThrowsNullPointerException() {
        CertificateSubjectName csn = new CertificateSubjectName((X500Name) null);
        boolean thrown = false;
        try {
            csn.get(null);
        } catch (NullPointerException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("null name must not produce IOException", e);
        }
        check(thrown, "get(null) must throw NullPointerException");
    }

    public static void main(String[] args) throws Exception {
        testUnrecognizedNameThrowsIOException();
        testNameConstantNotRecognized();
        testDnNameAccessorIsCaseInsensitive();
        testPrincipalAccessorWithNullNameReturnsNull();
        testNullAttributeNameThrowsNullPointerException();
    }
}
