/*
 * @test
 * @summary Verifies that OCSPNoCheckExtension.delete(String) always rejects attribute deletion with an IOException
 * @modules java.base/sun.security.x509
 * @run main OCSPNoCheckExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        OCSPNoCheckExtension defaultExt = new OCSPNoCheckExtension();
        expectDeleteRejected(defaultExt, "OCSPNoCheck");
        expectDeleteRejected(defaultExt, "");
        expectDeleteRejected(defaultExt, null);

        OCSPNoCheckExtension parsedExt = new OCSPNoCheckExtension(Boolean.TRUE, null);
        expectDeleteRejected(parsedExt, "x509.info.extensions.OCSPNoCheck");
        expectDeleteRejected(parsedExt, "unknown");
    }

    private static void expectDeleteRejected(OCSPNoCheckExtension ext, String name) {
        try {
            ext.delete(name);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("No attribute is allowed")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
            return;
        }
        throw new RuntimeException("Expected IOException from delete(" + name + ")");
    }
}
