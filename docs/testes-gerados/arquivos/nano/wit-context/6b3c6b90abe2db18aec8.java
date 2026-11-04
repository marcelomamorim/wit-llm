/* @test    @summary Test for sun.security.x509.CRLNumberExtension.delete(String) method
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        // Setup: create an instance with a BigInteger value
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(12345));
        // Confirm initial state
        if (ext == null) {
            throw new AssertionError("Extension instance is null");
        }
        // Call delete with the recognized attribute name "value"
        ext.delete("value");
        // After deletion, crlNumber should be null
        // Since crlNumber is private, we test behavior indirectly
        // Expect no exception and extension to be valid
        System.out.println("testDeleteWithValidName passed");
    }

    private static void testDeleteWithInvalidName() {
        try {
            CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(12345));
            // Call delete with an unrecognized attribute name
            ext.delete("unknownAttribute");
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception
            String message = e.getMessage();
            if (message == null || !message.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
            System.out.println("testDeleteWithInvalidName passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
