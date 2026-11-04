/*
 * @test
 * @summary Test that OCSPNoCheckExtension.set(String,Object) always throws IOException
 * @run main OCSPNoCheckExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        OCSPNoCheckExtension ext = new OCSPNoCheckExtension();
        boolean threw = false;
        try {
            ext.set("anyName", new Object());
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("No attribute is allowed")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException was not thrown by set()");
        }
    }
}
