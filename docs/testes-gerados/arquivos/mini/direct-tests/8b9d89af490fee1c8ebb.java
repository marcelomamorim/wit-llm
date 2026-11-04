/*
 * @test
 * @summary Test sun.security.x509.InhibitAnyPolicyExtension.set method
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import sun.security.x509.InhibitAnyPolicyExtension;
import java.io.IOException;

public class InhibitAnyPolicyExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetValidSkipCerts();
        testSetSkipCertsMinusOne();
        testSetInvalidSkipCertsBelowMinusOne();
        testSetInvalidType();
        testSetInvalidName();
    }

    private static void testSetValidSkipCerts() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        ext.set("skip_certs", Integer.valueOf(5));
        // No exception means success
    }

    private static void testSetSkipCertsMinusOne() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        ext.set("skip_certs", Integer.valueOf(-1));
        // No exception means success
    }

    private static void testSetInvalidSkipCertsBelowMinusOne() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("skip_certs", Integer.valueOf(-2));
            throw new RuntimeException("Expected IOException for skipCerts < -1");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testSetInvalidType() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("skip_certs", "not an integer");
            throw new RuntimeException("Expected IOException for non-Integer value");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testSetInvalidName() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("invalid_name", Integer.valueOf(1));
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            // Expected
        }
    }
}
