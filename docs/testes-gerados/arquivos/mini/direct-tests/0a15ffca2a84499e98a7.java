/*
 * @test
 * @summary Test InvalidityDateExtension(Date) constructor behavior
 * @run main InvalidityDateExtensionWitupTest
 */

import sun.security.x509.InvalidityDateExtension;
import java.util.Date;
import java.io.IOException;

public class InvalidityDateExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidDate();
        testConstructorWithNullDate();
    }

    private static void testConstructorWithValidDate() throws IOException {
        Date now = new Date();
        InvalidityDateExtension ext = new InvalidityDateExtension(now);
        if (ext == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }

    private static void testConstructorWithNullDate() throws IOException {
        InvalidityDateExtension ext = new InvalidityDateExtension((Date) null);
        if (ext == null) {
            throw new RuntimeException("Constructor returned null for null date");
        }
    }
}
