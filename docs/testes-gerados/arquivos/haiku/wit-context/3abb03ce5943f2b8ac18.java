/*
 * @test
 * @summary Witup-derived tests for the X500Principal(String) constructor
 * @run main X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testRfc2253RoundTrip();
        testMalformedNameThrowsIAE();
        testEqualNamesAreEqual();
        testDifferentNamesAreNotEqual();
    }

    static void testNullNameThrowsNPE() {
        try {
            new X500Principal((String) null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null name");
    }

    static void testRfc2253RoundTrip() {
        String dn = "CN=Duke,OU=JavaSoft,O=Sun Microsystems,C=US";
        X500Principal p = new X500Principal(dn);
        String actual = p.getName(X500Principal.RFC2253);
        if (!dn.equals(actual)) {
            throw new RuntimeException("Unexpected RFC2253 name: " + actual);
        }
    }

    static void testMalformedNameThrowsIAE() {
        try {
            new X500Principal("CN");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for malformed name");
    }

    static void testEqualNamesAreEqual() {
        X500Principal a = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        X500Principal b = new X500Principal("CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US");
        if (!a.equals(b)) {
            throw new RuntimeException("Identical names should be equal");
        }
        if (a.hashCode() != b.hashCode()) {
            throw new RuntimeException("Equal principals should have equal hash codes");
        }
    }

    static void testDifferentNamesAreNotEqual() {
        X500Principal a = new X500Principal("CN=Duke, C=US");
        X500Principal b = new X500Principal("CN=Other, C=US");
        if (a.equals(b)) {
            throw new RuntimeException("Different names should not be equal");
        }
    }
}
