/* @test    @summary Test for InvalidityDateExtension constructor with Date parameter    @run main InvalidityDateExtensionWitupTest */

import sun.security.x509.InvalidityDateExtension;
import java.util.Date;
import java.io.IOException;

public class InvalidityDateExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidDate();
        testConstructorWithNullDate();
    }

    private static void testConstructorWithValidDate() throws IOException {
        Date date = new Date();
        InvalidityDateExtension ext = new InvalidityDateExtension(date);
        if (ext == null) {
            throw new AssertionError("Constructor returned null for valid date");
        }
    }

    private static void testConstructorWithNullDate() {
        try {
            new InvalidityDateExtension((Date) null);
            throw new AssertionError("Expected NullPointerException or similar for null date");
        } catch (NullPointerException | IOException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
