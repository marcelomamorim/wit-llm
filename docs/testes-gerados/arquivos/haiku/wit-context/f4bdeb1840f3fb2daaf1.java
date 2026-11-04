/*
 * @test
 * @summary Verify NetscapeCertTypeExtension.encode produces deterministic DER output
 * @modules java.base/sun.security.x509
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        boolean[] bits = new boolean[8];
        bits[0] = true;
        bits[2] = true;

        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ext.encode(out1);
        byte[] enc1 = out1.toByteArray();
        if (enc1.length == 0) {
            throw new RuntimeException("empty encoding");
        }
        if (enc1[0] != 0x30) {
            throw new RuntimeException("encoding does not start with SEQUENCE tag");
        }

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        ext.encode(out2);
        if (!Arrays.equals(enc1, out2.toByteArray())) {
            throw new RuntimeException("repeated encode changed output");
        }

        NetscapeCertTypeExtension ext2 = new NetscapeCertTypeExtension(bits);
        ByteArrayOutputStream out3 = new ByteArrayOutputStream();
        ext2.encode(out3);
        if (!Arrays.equals(enc1, out3.toByteArray())) {
            throw new RuntimeException("equal inputs produced different encodings");
        }
    }
}
