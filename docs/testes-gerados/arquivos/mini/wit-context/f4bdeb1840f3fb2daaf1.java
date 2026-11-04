/*
 * @test
 * @summary Test NetscapeCertTypeExtension.encode(OutputStream) for normal and edge cases
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithDefaultConstructor();
        testEncodeWithBitStringConstructor();
        testEncodeWithNullOutputStream();
    }

    private static void testEncodeWithDefaultConstructor() throws IOException {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty");
        }
    }

    private static void testEncodeWithBitStringConstructor() throws IOException {
        boolean[] bits = new boolean[8];
        bits[0] = true; // set first bit
        bits[7] = true; // set last bit
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty");
        }
    }

    private static void testEncodeWithNullOutputStream() {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        try {
            ext.encode(null);
            throw new RuntimeException("Expected NullPointerException when passing null OutputStream");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException when passing null OutputStream", e);
        }
    }
}
