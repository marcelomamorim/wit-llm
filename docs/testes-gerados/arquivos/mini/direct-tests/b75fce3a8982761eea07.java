/*
 * @test
 * @summary Test sun.security.x509.InvalidityDateExtension.delete(String) method
 * @run main InvalidityDateExtensionWitupTest
 */

import sun.security.x509.InvalidityDateExtension;
import java.io.IOException;
import java.util.Date;

public class InvalidityDateExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        Date now = new Date();
        InvalidityDateExtension ext = new InvalidityDateExtension(now);
        ext.delete("date");
        // After delete("date"), the date field should be null and extensionValue updated
        // We cannot access date or extensionValue directly, but no exception means success
        // Try deleting again to ensure no exception when date is already null
        ext.delete("DATE"); // case insensitive
    }

    private static void testDeleteWithInvalidName() {
        try {
            Date now = new Date();
            InvalidityDateExtension ext = new InvalidityDateExtension(now);
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Name not supported by InvalidityDateExtension".equals(msg)) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
