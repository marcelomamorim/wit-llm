/*
 * @test
 * @summary Verify AuthorityInfoAccessExtension.encode writes a stable DER SEQUENCE
 * @modules java.base/sun.security.x509
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sun.security.x509.AccessDescription;
import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        List<AccessDescription> list = new ArrayList<>();
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ext.encode(out1);
        byte[] b1 = out1.toByteArray();
        if (b1.length == 0) {
            throw new RuntimeException("encode produced no bytes");
        }
        if (b1[0] != 0x30) {
            throw new RuntimeException("encoding does not start with SEQUENCE tag: " + b1[0]);
        }

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        ext.encode(out2);
        byte[] b2 = out2.toByteArray();
        if (!Arrays.equals(b1, b2)) {
            throw new RuntimeException("repeated encode produced different bytes");
        }
    }
}
