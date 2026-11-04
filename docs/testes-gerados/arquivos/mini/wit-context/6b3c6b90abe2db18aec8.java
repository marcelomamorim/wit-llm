/*
 * @test
 * @summary Test sun.security.x509.CRLNumberExtension.delete(String) method behavior
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        // Create with a valid CRL number
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(12345));
        // Delete with the recognized attribute name "value" (case insensitive)
        ext.delete("value");
        // After deletion, the crlNumber should be null, so encoding should produce null extensionValue
        // We cannot access extensionValue directly, but no exception means success
        // Try deleting again with "VALUE" to test case insensitivity
        ext.delete("VALUE");
    }

    private static void testDeleteWithInvalidName() throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(1);
        boolean threw = false;
        try {
            ext.delete("invalidName");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException was not thrown for invalid attribute name");
        }
    }
}
