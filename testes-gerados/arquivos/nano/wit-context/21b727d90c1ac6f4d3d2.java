/*
 * @test
 * @summary Test for OCSPNoCheckExtension.set method exception path
 * @run main OCSPNoCheckExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    public static void main(String[] args) {
        testSetThrowsIOException();
    }

    public static void testSetThrowsIOException() {
        OCSPNoCheckExtension ext = null;
        try {
            ext = new OCSPNoCheckExtension();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create OCSPNoCheckExtension", e);
        }
        try {
            ext.set("any", new Object());
            // Expecting IOException, so if no exception, fail the test
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Test passes if IOException is caught
            if (!e.getMessage().contains("No attribute is allowed")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
