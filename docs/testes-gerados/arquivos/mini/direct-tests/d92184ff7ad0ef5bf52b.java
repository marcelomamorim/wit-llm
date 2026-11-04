/*
 * @test
 * @summary Test sun.security.x509.InvalidityDateExtension.get(String) method
 * @run main InvalidityDateExtensionWitupTest
 */

import sun.security.x509.InvalidityDateExtension;
import java.util.Date;
import java.io.IOException;

public class InvalidityDateExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidNameAndDate();
        testGetWithValidNameAndNullDate();
        testGetWithInvalidName();
    }

    private static void testGetWithValidNameAndDate() throws IOException {
        Date now = new Date();
        InvalidityDateExtension ext = new InvalidityDateExtension(false, now);
        Date returned = ext.get("date");
        if (returned == null) {
            throw new RuntimeException("Returned date should not be null");
        }
        if (returned == now) {
            throw new RuntimeException("Returned date should be a clone, not the same instance");
        }
        if (returned.getTime() != now.getTime()) {
            throw new RuntimeException("Returned date time does not match original");
        }
    }

    private static void testGetWithValidNameAndNullDate() throws IOException {
        InvalidityDateExtension ext = new InvalidityDateExtension(false, (Date) null);
        Date returned = ext.get("date");
        if (returned != null) {
            throw new RuntimeException("Returned date should be null when original date is null");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            InvalidityDateExtension ext = new InvalidityDateExtension(false, new Date());
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException for unsupported name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Name not supported")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
