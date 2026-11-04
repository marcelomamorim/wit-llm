/*
 * @test
 * @summary OCSPNoCheckExtension.get must always throw IOException
 * @modules java.base/sun.security.x509
 * @run main OCSPNoCheckExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        OCSPNoCheckExtension ext = new OCSPNoCheckExtension();
        expectIOException(ext, "OCSPNoCheck");
        expectIOException(ext, "");
        expectIOException(ext, null);

        OCSPNoCheckExtension ext2 = new OCSPNoCheckExtension(Boolean.TRUE, null);
        expectIOException(ext2, OCSPNoCheckExtension.NAME);

        OCSPNoCheckExtension ext3 = new OCSPNoCheckExtension(Boolean.FALSE, new Object());
        expectIOException(ext3, OCSPNoCheckExtension.IDENT);
    }

    private static void expectIOException(OCSPNoCheckExtension e, String name) {
        try {
            Object result = e.get(name);
            throw new RuntimeException("Expected IOException but get returned: " + result);
        } catch (IOException expected) {
            String msg = expected.getMessage();
            if (msg == null || !msg.contains("OCSPNoCheckExtension")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
