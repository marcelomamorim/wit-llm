/*
 * @test
 * @summary Checks that NameConstraintsExtension.encode(OutputStream) emits a DER SEQUENCE and is repeatable when the extension value is already set
 * @modules java.base/sun.security.x509
 * @run main NameConstraintsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import sun.security.x509.NameConstraintsExtension;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] value = new byte[] {0x30, 0x00};
        NameConstraintsExtension ext = new NameConstraintsExtension(Boolean.TRUE, value);

        ByteArrayOutputStream first = new ByteArrayOutputStream();
        ext.encode(first);
        byte[] encoded = first.toByteArray();
        if (encoded.length == 0) {
            throw new RuntimeException("encode produced no bytes");
        }
        if (encoded[0] != 0x30) {
            throw new RuntimeException("encoding does not start with a DER SEQUENCE tag");
        }

        ByteArrayOutputStream second = new ByteArrayOutputStream();
        ext.encode(second);
        if (!Arrays.equals(encoded, second.toByteArray())) {
            throw new RuntimeException("repeated encode produced different bytes");
        }
    }
}
