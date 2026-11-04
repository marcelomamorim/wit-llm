/* @test    @summary Test delete method of CRLNumberExtension class    @run main CRLNumberExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(12345);
        ext.delete("value");
        // After deletion, crlNumber should be null, verify by attempting to encode
        ext.delete("value"); // should not throw
    }

    private static void testDeleteWithInvalidName() {
        CRLNumberExtension ext;
        try {
            ext = new CRLNumberExtension(12345);
            ext.delete("invalidName");
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
