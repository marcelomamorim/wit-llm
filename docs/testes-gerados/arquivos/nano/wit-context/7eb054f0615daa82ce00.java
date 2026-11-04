/* @test    @summary Test for sun.security.x509.Extension.encode method handling null extensionId and extensionValue  @run main sun.security.x509.ExtensionWitupTest */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.Extension;
import sun.security.util.ObjectIdentifier;

public class ExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionId();
        testEncodeWithNullExtensionValue();
        testEncodeNormal();
    }

    static void testEncodeWithNullExtensionId() {
        Extension ext = new Extension();
        try {
            ext.extensionId = null;
            ext.extensionValue = new byte[] {1, 2, 3};
            DerOutputStream out = new DerOutputStream();
            ext.encode(out);
            throw new AssertionError("Expected IOException for null extensionId");
        } catch (IOException e) {
            assert e.getMessage().contains("Null OID") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testEncodeWithNullExtensionValue() throws Exception {
        Extension ext = new Extension();
        ext.extensionId = new ObjectIdentifier("1.2.3.4");
        ext.extensionValue = null;
        try {
            DerOutputStream out = new DerOutputStream();
            ext.encode(out);
            throw new AssertionError("Expected IOException for null extensionValue");
        } catch (IOException e) {
            assert e.getMessage().contains("No value") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testEncodeNormal() throws Exception {
        Extension ext = new Extension();
        ext.extensionId = new ObjectIdentifier("1.2.3.4");
        ext.critical = true;
        ext.extensionValue = new byte[] {10, 20, 30};
        DerOutputStream out = new DerOutputStream();
        ext.encode(out);
        byte[] encoded = out.toByteArray();
        assert encoded != null && encoded.length > 0 : "Encoding failed or empty";
    }
}
