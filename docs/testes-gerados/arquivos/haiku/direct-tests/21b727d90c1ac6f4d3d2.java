/*
 * @test
 * @summary OCSPNoCheckExtension.set must always reject attribute assignment with an IOException
 * @modules java.base/sun.security.x509
 * @run main OCSPNoCheckExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        OCSPNoCheckExtension ext = new OCSPNoCheckExtension();
        checkSetThrows(ext, "anything", null);
        checkSetThrows(ext, null, new Object());
        checkSetThrows(ext, "x509.info.extensions.OCSPNoCheck", Boolean.TRUE);
    }

    static void checkSetThrows(OCSPNoCheckExtension ext, String name, Object obj) {
        try {
            ext.set(name, obj);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.startsWith("No attribute is allowed")) {
                throw new RuntimeException("Unexpected message: " + msg);
            }
            return;
        }
        throw new RuntimeException("set() did not throw IOException");
    }
}
