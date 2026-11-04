/*
 * @test
 * @summary Verify that OCSPNoCheckExtension.get(String) always throws IOException with the documented message
 * @modules java.base/sun.security.x509
 * @run main OCSPNoCheckExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        OCSPNoCheckExtension ext = new OCSPNoCheckExtension();
        String[] names = {"OCSPNoCheck", "", "x509.info.extensions.OCSPNoCheck", "unknown", null};
        for (String name : names) {
            try {
                Object value = ext.get(name);
                throw new RuntimeException("No IOException for name=" + name + " value=" + value);
            } catch (IOException e) {
                String msg = e.getMessage();
                if (msg == null || !msg.startsWith("No attribute is allowed")) {
                    throw new RuntimeException("Unexpected message for name=" + name + ": " + msg);
                }
            }
        }
    }
}
