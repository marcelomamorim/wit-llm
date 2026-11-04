/*
 * @test
 * @summary Witup-derived checks for AuthorityInfoAccessExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main AuthorityInfoAccessExtensionWitupTest
 */
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collections;
import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        AuthorityInfoAccessExtension ext =
                new AuthorityInfoAccessExtension(Collections.emptyList());
        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ext.encode(out1);
        byte[] first = out1.toByteArray();
        if (first.length == 0 || first[0] != 0x30) {
            throw new RuntimeException("expected DER SEQUENCE output");
        }
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        ext.encode(out2);
        if (!Arrays.equals(first, out2.toByteArray())) {
            throw new RuntimeException("encode is not stable across calls");
        }
    }
}
