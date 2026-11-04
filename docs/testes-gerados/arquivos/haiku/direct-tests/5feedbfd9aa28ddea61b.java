/*
 * @test
 * @summary Checks that AuthorityKeyIdentifierExtension.encode(OutputStream) emits a DER SEQUENCE containing the key identifier bytes and is deterministic across repeated calls
 * @modules java.base/sun.security.x509
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.KeyIdentifier;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] keyBytes = {1, 2, 3, 4, 5, 6, 7, 8};
        AuthorityKeyIdentifierExtension ext =
                new AuthorityKeyIdentifierExtension(new KeyIdentifier(keyBytes), null, null);

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ext.encode(out1);
        byte[] enc1 = out1.toByteArray();

        if (enc1.length == 0) {
            throw new RuntimeException("encoding is empty");
        }
        if (enc1[0] != 0x30) {
            throw new RuntimeException("encoding does not start with SEQUENCE tag");
        }
        if (indexOf(enc1, keyBytes) < 0) {
            throw new RuntimeException("encoding does not contain key identifier bytes");
        }

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        ext.encode(out2);
        byte[] enc2 = out2.toByteArray();
        if (!Arrays.equals(enc1, enc2)) {
            throw new RuntimeException("repeated encode produced different bytes");
        }
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
