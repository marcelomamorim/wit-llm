/* @test    @summary Test for sun.security.x509.InvalidityDateExtension.delete(String) method
 * @run main InvalidityDateExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithSupportedName();
        testDeleteWithUnsupportedName();
    }

    private static void testDeleteWithSupportedName() throws Exception {
        // Setup: create an instance with a valid date
        Date date = new java.util.Date();
        InvalidityDateExtension ext = new InvalidityDateExtension(false, date);
        // Call delete with supported name ("date")
        ext.delete("date");
        // Verify: date should be null after deletion
        if (ext.getClass().getDeclaredField("date").get(null) != null) {
            throw new AssertionError("Expected date to be null after delete with 'date'");
        }
    }

    private static void testDeleteWithUnsupportedName() {
        try {
            // Setup: create an instance
            Date date = new java.util.Date();
            InvalidityDateExtension ext = new InvalidityDateExtension(false, date);
            // Call delete with unsupported name
            ext.delete("unsupportedName");
            throw new AssertionError("Expected IOException for unsupported name");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().equals("Name not supported by InvalidityDateExtension")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
