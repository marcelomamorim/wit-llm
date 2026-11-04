/* @test    @summary Test OCSPNoCheckExtension.delete method throws IOException for any attribute name    @run main OCSPNoCheckExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    public static void main(String[] args) {
        testDeleteThrowsIOException();
    }

    public static void testDeleteThrowsIOException() {
        OCSPNoCheckExtension ext = null;
        try {
            ext = new OCSPNoCheckExtension();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create OCSPNoCheckExtension instance", e);
        }
        String[] testNames = {"anyName", "", null};
        for (String name : testNames) {
            try {
                ext.delete(name);
                throw new AssertionError("Expected IOException not thrown for name: " + name);
            } catch (IOException e) {
                if (!e.getMessage().contains("No attribute is allowed")) {
                    throw new AssertionError("Unexpected exception message for name: " + name, e);
                }
            }
        }
    }
}
