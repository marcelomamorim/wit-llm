/* @test    @summary Test encode method of ExtendedKeyUsageExtension for correct output and exception handling    @run main ExtendedKeyUsageExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.ObjectIdentifier;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithValidExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() {
        try {
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length == 0) {
                System.out.println("PASS: encode with null extensionValue produces output");
            } else {
                System.out.println("FAIL: encode with null extensionValue produces unexpected output");
            }
        } catch (IOException e) {
            System.out.println("FAIL: encode threw IOException: " + e.getMessage());
        }
    }

    private static void testEncodeWithValidExtensionValue() {
        try {
            ObjectIdentifier oid1 = new ObjectIdentifier("1.3.6.1.5.5.7.3.1");
            ObjectIdentifier oid2 = new ObjectIdentifier("1.3.6.1.5.5.7.3.2");
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
            ext.extensionValue = null; // forcibly null to trigger encodeThis() in encode
            ext.encodeThis = () -> {
                ext.extensionValue = new byte[] {0x30, 0x0A, 0x06, 0x08, 0x2B, 0x06, 0x01, 0x05, 0x05, 0x07, 0x03, 0x01};
            };
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result != null && result.length > 0) {
                System.out.println("PASS: encode with valid extensionValue produces output");
            } else {
                System.out.println("FAIL: encode with valid extensionValue produces no output");
            }
        } catch (IOException e) {
            System.out.println("FAIL: encode threw IOException: " + e.getMessage());
        }
    }
}
