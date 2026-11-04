/*
 * @test
 * @summary Test sun.security.x509.InvalidityDateExtension.get(String) method
 * @run main InvalidityDateExtensionWitupTest
 */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidNameAndDate();
        testGetWithValidNameAndNullDate();
        testGetWithInvalidNameThrowsIOException();
    }

    private static void testGetWithValidNameAndDate() throws IOException {
        Date now = new Date();
        InvalidityDateExtension ext = new InvalidityDateExtension(false, now);
        Date returned = ext.get("date");
        if (returned == null) {
            throw new RuntimeException("Returned date should not be null when initialized with non-null date");
        }
        if (returned.equals(now) == false) {
            throw new RuntimeException("Returned date should be equal to the original date");
        }
        if (returned == now) {
            throw new RuntimeException("Returned date should be a clone, not the same instance");
        }
    }

    private static void testGetWithValidNameAndNullDate() throws IOException {
        InvalidityDateExtension ext = new InvalidityDateExtension(false, (Date) null);
        Date returned = ext.get("date");
        if (returned != null) {
            throw new RuntimeException("Returned date should be null when initialized with null date");
        }
    }

    private static void testGetWithInvalidNameThrowsIOException() {
        try {
            InvalidityDateExtension ext = new InvalidityDateExtension(false, new Date());
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException was not thrown for invalid name");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
