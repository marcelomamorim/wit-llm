/*
 * @test
 * @summary Verifies that NetscapeCertTypeExtension.encode(OutputStream) writes a deterministic DER SEQUENCE containing the Netscape-Cert-Type OID
 * @modules java.base/sun.security.x509
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {

    private static final byte[] OID_BYTES = {
        0x06, 0x09, 0x60, (byte) 0x86, 0x48, 0x01, (byte) 0x86, (byte) 0xF8, 0x42, 0x01, 0x01
    };

    private static byte[] encodeToBytes(NetscapeCertTypeExtension ext) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ext.encode(bos);
        return bos.toByteArray();
    }

    private static boolean containsSubarray(byte[] data, byte[] pattern) {
        outer:
        for (int i = 0; i + pattern.length <= data.length; i++) {
            for (int j = 0; j < pattern.length; j++) {
                if (data[i + j] != pattern[j]) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        boolean[] bits = new boolean[8];
        bits[0] = true;
        bits[1] = true;

        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);

        byte[] first = encodeToBytes(ext);
        byte[] second = encodeToBytes(ext);

        if (first.length == 0) {
            throw new RuntimeException("encode produced no output");
        }
        if (first[0] != 0x30) {
            throw new RuntimeException("expected DER SEQUENCE tag 0x30, got " + first[0]);
        }
        if (!containsSubarray(first, OID_BYTES)) {
            throw new RuntimeException("encoded extension does not contain Netscape-Cert-Type OID");
        }
        if (!java.util.Arrays.equals(first, second)) {
            throw new RuntimeException("repeated encode of the same extension differs");
        }

        boolean[] otherBits = new boolean[8];
        otherBits[2] = true;
        NetscapeCertTypeExtension other = new NetscapeCertTypeExtension(otherBits);
        byte[] otherEncoded = encodeToBytes(other);
        if (java.util.Arrays.equals(first, otherEncoded)) {
            throw new RuntimeException("different bit strings produced identical encodings");
        }
    }
}
