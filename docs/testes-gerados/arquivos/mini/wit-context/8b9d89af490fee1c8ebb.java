/*
 * @test
 * @summary Test sun.security.x509.InhibitAnyPolicyExtension.set(String,Object) method
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import sun.security.x509.InhibitAnyPolicyExtension;
import java.io.IOException;

public class InhibitAnyPolicyExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetValidSkipCerts();
        testSetSkipCertsMinusOne();
        testSetInvalidSkipCertsValue();
        testSetInvalidType();
        testSetInvalidName();
    }

    private static void testSetValidSkipCerts() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        ext.set("skip_certs", Integer.valueOf(5));
        // No exception means pass
    }

    private static void testSetSkipCertsMinusOne() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        ext.set("skip_certs", Integer.valueOf(-1));
        // No exception means pass; skipCerts internally set to Integer.MAX_VALUE
    }

    private static void testSetInvalidSkipCertsValue() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("skip_certs", Integer.valueOf(-2));
            throw new RuntimeException("Expected IOException for invalid skipCerts value not thrown");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testSetInvalidType() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("skip_certs", "not an integer");
            throw new RuntimeException("Expected IOException for invalid type not thrown");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testSetInvalidName() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("invalid_name", Integer.valueOf(1));
            throw new RuntimeException("Expected IOException for invalid name not thrown");
        } catch (IOException e) {
            // Expected
        }
    }
}
