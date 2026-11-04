/*
 * @test
 * @summary WITUP-derived check that CRLNumberExtension.delete accepts the "value" attribute case-insensitively and rejects other names with IOException
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(7);

        ext.delete("value");
        ext.delete("VALUE");

        CRLNumberExtension ext2 = new CRLNumberExtension(7);
        boolean thrown = false;
        try {
            ext2.delete("other");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        }
    }
}
