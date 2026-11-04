/*
 * @test
 * @summary Test sun.security.x509.CRLNumberExtension.delete(String) method
 * @run main CRLNumberExtensionWitupTest
 */

import sun.security.x509.CRLNumberExtension;
import sun.security.x509.PKIXExtensions;
import java.math.BigInteger;
import java.io.IOException;

public class CRLNumberExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteValidName();
        testDeleteInvalidName();
    }

    private static void testDeleteValidName() throws Exception {
        // Create with a valid CRL number
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(12345));
        // Delete the NUMBER attribute
        ext.delete("value");
        // After deletion, the crlNumber should be null and extensionValue null
        // We cannot access crlNumber directly, but extensionValue is protected in Extension
        // So we test by trying to delete again and expect no exception for "value"
        // and exception for other names
        try {
            ext.delete("value"); // should not throw
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException on second delete with valid name", e);
        }
    }

    private static void testDeleteInvalidName() throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(1);
        boolean threw = false;
        try {
            ext.delete("invalidName");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized") || !msg.contains("CRLNumber")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        }
    }
}
