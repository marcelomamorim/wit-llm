/*
 * @test
 * @summary Test NetscapeCertTypeExtension.encode(OutputStream) behavior
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithPreSetExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        // extensionValue is null initially
        ext.extensionValue = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeWithPreSetExtensionValue() throws IOException {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        // set extensionValue to non-null dummy value
        ext.extensionValue = new byte[] { 0x01, 0x02, 0x03 };
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue is preset");
        }
    }
}