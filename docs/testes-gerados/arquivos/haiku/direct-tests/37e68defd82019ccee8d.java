/*
 * @test
 * @summary Checks DER encoding produced by InhibitAnyPolicyExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] a = encode(new InhibitAnyPolicyExtension(5));
        check(a.length > 0, "encoding must not be empty");
        check((a[0] & 0xFF) == 0x30, "outer encoding must be a DER SEQUENCE");
        check(contains(a, new byte[]{0x06, 0x03, 0x55, 0x1D, 0x36}),
              "encoding must contain OID 2.5.29.54");
        check(contains(a, new byte[]{0x02, 0x01, 0x05}),
              "encoding must contain INTEGER skipCerts=5");

        byte[] again = encode(new InhibitAnyPolicyExtension(5));
        check(java.util.Arrays.equals(a, again), "repeated encoding must be identical");

        byte[] other = encode(new InhibitAnyPolicyExtension(7));
        check(!java.util.Arrays.equals(a, other), "different skipCerts must produce different encodings");
        check(contains(other, new byte[]{0x02, 0x01, 0x07}),
              "encoding must contain INTEGER skipCerts=7");

        byte[] unlimited = encode(new InhibitAnyPolicyExtension(-1));
        check(contains(unlimited, new byte[]{0x02, 0x04, 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}),
              "skipCerts=-1 must encode Integer.MAX_VALUE");
    }

    private static byte[] encode(InhibitAnyPolicyExtension ext) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ext.encode(bos);
        return bos.toByteArray();
    }

    private static boolean contains(byte[] haystack, byte[] needle) {
        outer:
        for (int i = 0; i + needle.length <= haystack.length; i++) {
            for (int j = 0; j < needle.length; j++) {
                if (haystack[i + j] != needle[j]) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
