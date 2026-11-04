/*
 * @test
 * @summary Test OCSPNoCheckExtension.set method throws IOException as expected
 * @run main OCSPNoCheckExtensionWitupTest
 */

import sun.security.x509.OCSPNoCheckExtension;
import java.io.IOException;

public class OCSPNoCheckExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        OCSPNoCheckExtension ext = new OCSPNoCheckExtension();
        try {
            ext.set("anyName", new Object());
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("No attribute is allowed")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
