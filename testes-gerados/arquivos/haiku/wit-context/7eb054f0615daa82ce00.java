/*
 * @test
 * @summary Verifies that Extension.encode rejects an extension with a null OID using a public IOException
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ExtensionWitupTest
 */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.Extension;

public class ExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        Extension ext = new Extension();
        DerOutputStream out = new DerOutputStream();
        try {
            ext.encode(out);
            throw new RuntimeException("Expected IOException for null extension OID");
        } catch (IOException e) {
            String expected = "Null OID to encode for the extension!";
            if (!expected.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
