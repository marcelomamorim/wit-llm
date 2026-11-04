/*
 * @test
 * @summary Witup-derived checks for IssuingDistributionPointExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import sun.security.x509.IssuingDistributionPointExtension;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        IssuingDistributionPointExtension ext =
                new IssuingDistributionPointExtension(null, null, false, false, false, false);

        ByteArrayOutputStream first = new ByteArrayOutputStream();
        ext.encode(first);
        byte[] encoded = first.toByteArray();

        if (encoded.length == 0) {
            throw new RuntimeException("encode produced no bytes");
        }
        if ((encoded[0] & 0xFF) != 0x30) {
            throw new RuntimeException("expected DER SEQUENCE tag 0x30, got " + (encoded[0] & 0xFF));
        }

        ByteArrayOutputStream second = new ByteArrayOutputStream();
        ext.encode(second);
        byte[] encodedAgain = second.toByteArray();

        if (encoded.length != encodedAgain.length) {
            throw new RuntimeException("repeated encode changed length");
        }
        for (int i = 0; i < encoded.length; i++) {
            if (encoded[i] != encodedAgain[i]) {
                throw new RuntimeException("repeated encode changed content at index " + i);
            }
        }
    }
}
