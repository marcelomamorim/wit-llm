/*
 * @test
 * @summary Test sun.security.x509.OCSPNoCheckExtension.delete(String) throws IOException
 * @run main OCSPNoCheckExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        OCSPNoCheckExtension ext = new OCSPNoCheckExtension();
        try {
            ext.delete("anyName");
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("No attribute is allowed")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
