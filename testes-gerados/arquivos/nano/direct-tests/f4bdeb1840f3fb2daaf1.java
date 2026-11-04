/* @test    @summary Test encoding of NetscapeCertTypeExtension.    @run main NetscapeCertTypeExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {

    public static void main(String[] args) {
        testEncodeWithDefaultValues();
        testEncodeWithCustomValues();
    }

    private static void testEncodeWithDefaultValues() {
        try {
            NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] encoded = baos.toByteArray();
            if (encoded.length == 0) {
                throw new AssertionError("Encoded byte array should not be empty");
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during encode", e);
        }
    }

    private static void testEncodeWithCustomValues() {
        try {
            boolean[] bits = { true, false, true, false, true, false, true, false };
            NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] encoded = baos.toByteArray();
            if (encoded.length == 0) {
                throw new AssertionError("Encoded byte array should not be empty");
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException during encode", e);
        }
    }
}
