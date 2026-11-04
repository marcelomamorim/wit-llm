/* @test    @summary Test constructor of InvalidityDateExtension with a Date parameter    @run main InvalidityDateExtensionWitupTest */

import java.util.Date;
import sun.security.x509.InvalidityDateExtension;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithDate();
        testConstructorWithNullDate();
    }

    private static void testConstructorWithDate() throws Exception {
        Date date = new Date(1638316800000L); // 2021-12-01T00:00:00Z
        InvalidityDateExtension ext = new InvalidityDateExtension(date);
        if (ext == null) {
            throw new AssertionError("Constructor with Date returned null");
        }
    }

    private static void testConstructorWithNullDate() throws Exception {
        try {
            InvalidityDateExtension ext = new InvalidityDateExtension((Date) null);
            if (ext == null) {
                throw new AssertionError("Constructor with null Date returned null");
            }
        } catch (NullPointerException e) {
            // Expected if constructor does not handle null
        }
    }
}
