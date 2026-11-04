/*
 * @test
 * @summary Test sun.security.x509.Extension.encode method behavior
 * @run main ExtensionWitupTest
 */

import sun.security.x509.Extension;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class ExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidFields();
        testEncodeWithNullExtensionId();
        testEncodeWithNullExtensionValue();
    }

    private static void testEncodeWithValidFields() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3, 4});
        byte[] value = new byte[] {0x01, 0x02, 0x03};
        Extension ext = new Extension(oid, true, value);

        DerOutputStream out = new DerOutputStream();
        ext.encode(out);
        byte[] encoded = out.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is null or empty");
        }
    }

    private static void testEncodeWithNullExtensionId() {
        try {
            Extension ext = new Extension();
            ext.extensionId = null;
            ext.extensionValue = new byte[] {0x01};
            ext.critical = false;
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

    private static void testEncodeWithNullExtensionValue() {
        try {
            ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3});
            Extension ext = new Extension();
            ext.extensionId = oid;
            ext.extensionValue = null;
            ext.critical = false;
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
}
