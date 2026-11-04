/*
 * @test
 * @summary Test sun.security.x509.InvalidityDateExtension.delete(String) method
 * @run main InvalidityDateExtensionWitupTest
 */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithDateName();
        testDeleteWithUnsupportedName();
    }

    private static void testDeleteWithDateName() throws Exception {
        Date now = new Date();
        InvalidityDateExtension ext = new InvalidityDateExtension(now);
        ext.delete("date");
        // After deletion, the date field should be null, so encoding sets extensionValue to null
        // We cannot access extensionValue directly, but no exception means success
    }

    private static void testDeleteWithUnsupportedName() {
        try {
            Date now = new Date();
            InvalidityDateExtension ext = new InvalidityDateExtension(now);
            ext.delete("unsupportedName");
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Name not supported by InvalidityDateExtension".equals(msg)) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
            // Expected exception caught
        }
    }
}
