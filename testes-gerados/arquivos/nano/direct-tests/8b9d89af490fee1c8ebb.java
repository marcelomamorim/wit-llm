/* @test    @summary Test InhibitAnyPolicyExtension.set method for valid and invalid inputs    @run main InhibitAnyPolicyExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidValue();
        testSetWithNegativeValue();
        testSetWithUnlimited();
        testSetWithInvalidName();
    }

    static void testSetWithValidValue() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        ext.set("skip_certs", Integer.valueOf(5));
        // Expect skipCerts to be 5
        if (ext.skipCerts != 5) {
            throw new AssertionError("Expected skipCerts to be 5");
        }
    }

    static void testSetWithNegativeValue() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("skip_certs", Integer.valueOf(-2));
            throw new AssertionError("Expected IOException for negative value");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("Invalid value for skipCerts")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    static void testSetWithUnlimited() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        ext.set("skip_certs", Integer.valueOf(-1));
        // Expect skipCerts to be Integer.MAX_VALUE
        if (ext.skipCerts != Integer.MAX_VALUE) {
            throw new AssertionError("Expected skipCerts to be Integer.MAX_VALUE");
        }
    }

    static void testSetWithInvalidName() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.set("invalid_name", Integer.valueOf(1));
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
