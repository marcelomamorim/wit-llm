/*
 * @test
 * @summary Witup generated checks for CRLNumberExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] one = encode(new CRLNumberExtension(1));
        byte[] oneAgain = encode(new CRLNumberExtension(1));
        if (!Arrays.equals(one, oneAgain)) {
            throw new RuntimeException("encoding is not deterministic");
        }
        if (one.length == 0 || (one[0] & 0xff) != 0x30) {
            throw new RuntimeException("extension is not a DER SEQUENCE");
        }
        if (indexOf(one, new byte[]{0x06, 0x03, 0x55, 0x1D, 0x14}) < 0) {
            throw new RuntimeException("CRLNumber OID 2.5.29.20 not found");
        }
        if (indexOf(one, new byte[]{0x02, 0x01, 0x01}) < 0) {
            throw new RuntimeException("INTEGER 1 not found");
        }

        byte[] two = encode(new CRLNumberExtension(BigInteger.valueOf(2)));
        if (indexOf(two, new byte[]{0x02, 0x01, 0x02}) < 0) {
            throw new RuntimeException("INTEGER 2 not found");
        }
        if (Arrays.equals(one, two)) {
            throw new RuntimeException("different CRL numbers encoded identically");
        }

        byte[] big = encode(new CRLNumberExtension(BigInteger.valueOf(256)));
        if (indexOf(big, new byte[]{0x02, 0x02, 0x01, 0x00}) < 0) {
            throw new RuntimeException("INTEGER 256 not found");
        }
    }

    private static byte[] encode(CRLNumberExtension ext) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ext.encode(out);
        return out.toByteArray();
    }

    private static int indexOf(byte[] haystack, byte[] needle) {
        outer:
        for (int i = 0; i <= haystack.length - needle.length; i++) {
            for (int j = 0; j < needle.length; j++) {
                if (haystack[i + j] != needle[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }
}
