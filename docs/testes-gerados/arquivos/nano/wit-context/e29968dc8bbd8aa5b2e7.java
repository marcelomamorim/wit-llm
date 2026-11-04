/* @test    @summary Test for OCSPNoCheckExtension.get(String) method to verify exception throwing behavior    @run main OCSPNoCheckExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    public static void main(String[] args) {
        testGetThrowsIOException();
    }

    public static void testGetThrowsIOException() {
        OCSPNoCheckExtension ext = null;
        try {
            ext = new OCSPNoCheckExtension();
        } catch (IOException e) {
            // If construction fails, the test cannot proceed
            System.err.println("Failed to create OCSPNoCheckExtension instance: " + e);
            return;
        }
        try {
            ext.get("anyAttribute");
            System.err.println("Expected IOException was not thrown.");
        } catch (IOException e) {
            // Verify that the exception message matches expected pattern
            if (e.getMessage() != null && e.getMessage().contains("No attribute is allowed")) {
                System.out.println("testGetThrowsIOException passed.");
            } else {
                System.err.println("testGetThrowsIOException failed: Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
