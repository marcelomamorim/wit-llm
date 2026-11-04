/*
 * @test
 * @summary Test sun.security.x509.Extension.encode(DerOutputStream) for exception and normal cases
 * @run main ExtensionWitupTest
 */

import sun.security.x509.Extension;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class ExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeNullExtensionId();
        testEncodeNullExtensionValue();
        testEncodeNormal();
    }

    static void testEncodeNullExtensionId() {
        try {
            Extension ext = new Extension();
            ext.extensionId = null;
            ext.extensionValue = new byte[] {1, 2, 3};
            DerOutputStream out = new DerOutputStream();
            ext.encode(out);
            throw new RuntimeException("Expected IOException for null extensionId not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Null OID to encode for the extension!".equals(msg)) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    static void testEncodeNullExtensionValue() {
        try {
            Extension ext = new Extension();
            ext.extensionId = ObjectIdentifier.newInternal(new int[] {1, 2, 3});
            ext.extensionValue = null;
            DerOutputStream out = new DerOutputStream();
            ext.encode(out);
            throw new RuntimeException("Expected IOException for null extensionValue not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"No value to encode for the extension!".equals(msg)) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    static void testEncodeNormal() throws IOException {
        Extension ext = new Extension();
        ext.extensionId = ObjectIdentifier.newInternal(new int[] {1, 2, 3});
        ext.extensionValue = new byte[] {4, 5, 6};
        ext.critical = true;
        DerOutputStream out = new DerOutputStream();
        ext.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }
}
