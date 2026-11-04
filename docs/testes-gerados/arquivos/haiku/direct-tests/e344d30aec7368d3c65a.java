/*
 * @test
 * @summary Verifies PrivateKeyUsageExtension.delete(String) for recognized and unrecognized attribute names
 * @modules java.base/sun.security.x509
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import java.security.cert.CertificateException;
import java.util.Date;
import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteNotBeforeExactName();
        testDeleteNotAfterCaseInsensitive();
        testDeleteNotBeforeMixedCase();
        testDeleteBothThenRepeat();
        testDeleteUnknownNameThrows();
        testDeleteEmptyNameThrows();
    }

    private static Date before() {
        return new Date(1000000000000L);
    }

    private static Date after() {
        return new Date(1500000000000L);
    }

    static void testDeleteNotBeforeExactName() throws Exception {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before(), after());
        ext.delete(PrivateKeyUsageExtension.NOT_BEFORE);
    }

    static void testDeleteNotAfterCaseInsensitive() throws Exception {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before(), after());
        ext.delete(PrivateKeyUsageExtension.NOT_AFTER.toUpperCase());
    }

    static void testDeleteNotBeforeMixedCase() throws Exception {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before(), after());
        ext.delete("Not_Before");
    }

    static void testDeleteBothThenRepeat() throws Exception {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before(), after());
        ext.delete(PrivateKeyUsageExtension.NOT_BEFORE);
        ext.delete(PrivateKeyUsageExtension.NOT_AFTER);
        ext.delete(PrivateKeyUsageExtension.NOT_BEFORE);
        ext.delete(PrivateKeyUsageExtension.NOT_AFTER);
    }

    static void testDeleteUnknownNameThrows() throws Exception {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before(), after());
        try {
            ext.delete("bogus");
            throw new RuntimeException("Expected CertificateException for unknown name");
        } catch (CertificateException e) {
            // expected
        }
    }

    static void testDeleteEmptyNameThrows() throws Exception {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(before(), after());
        try {
            ext.delete("");
            throw new RuntimeException("Expected CertificateException for empty name");
        } catch (CertificateException e) {
            // expected
        }
    }
}
