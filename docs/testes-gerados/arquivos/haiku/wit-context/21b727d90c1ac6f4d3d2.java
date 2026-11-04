/*
 * @test
 * @summary Verify OCSPNoCheckExtension.set always rejects attribute assignment with IOException
 * @modules java.base/sun.security.x509
 * @run main OCSPNoCheckExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        OCSPNoCheckExtension ext = new OCSPNoCheckExtension();
        expectIOException(ext, "OCSPNoCheck", null);
        expectIOException(ext, "OCSPNoCheck", "value");
        expectIOException(ext, "unknown", Boolean.TRUE);
        expectIOException(ext, null, null);
    }

    private static void expectIOException(OCSPNoCheckExtension ext, String name, Object obj) {
        try {
            ext.set(name, obj);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("No attribute is allowed")) {
                throw new RuntimeException("Unexpected message: " + msg);
            }
            return;
        }
        throw new RuntimeException("Expected IOException was not thrown");
    }
}
