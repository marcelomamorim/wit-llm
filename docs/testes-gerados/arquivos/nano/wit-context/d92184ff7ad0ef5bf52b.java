/* @test    @summary Test for sun.security.x509.InvalidityDateExtension.get(String) method
 * @run main InvalidityDateExtensionWitupTest
 */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidDate();
        testGetWithNullDate();
        testGetWithUnsupportedName();
    }

    private static void testGetWithValidDate() throws Exception {
        Date testDate = new Date(1630454400000L); // 2021-09-01T00:00:00Z
        InvalidityDateExtension ext = new InvalidityDateExtension(false, testDate);
        Date result = ext.get("date");
        if (result == null) {
            throw new AssertionError("Expected non-null date");
        }
        if (!result.equals(testDate)) {
            throw new AssertionError("Returned date does not match the input date");
        }
    }

    private static void testGetWithNullDate() throws Exception {
        Date nullDate = null;
        InvalidityDateExtension ext = new InvalidityDateExtension(false, nullDate);
        Date result = ext.get("date");
        if (result != null) {
            throw new AssertionError("Expected null date");
        }
    }

    private static void testGetWithUnsupportedName() {
        Date testDate = new Date();
        try {
            InvalidityDateExtension ext = new InvalidityDateExtension(false, testDate);
            ext.get("unsupported");
            throw new AssertionError("Expected IOException for unsupported name");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
