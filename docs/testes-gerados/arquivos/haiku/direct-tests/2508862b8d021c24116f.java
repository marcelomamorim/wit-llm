/*
 * @test
 * @summary Verify PolicyMappingsExtension.encode writes a well-formed DER SEQUENCE and is deterministic
 * @modules java.base/sun.security.x509
 * @run main PolicyMappingsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] value = {0x30, 0x00};
        PolicyMappingsExtension ext = new PolicyMappingsExtension(Boolean.TRUE, value);

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ext.encode(out1);
        byte[] enc1 = out1.toByteArray();

        check(enc1.length > 2, "encoded extension too short");
        check((enc1[0] & 0xff) == 0x30, "outer tag is not DER SEQUENCE");
        check((enc1[1] & 0xff) == enc1.length - 2,
              "outer SEQUENCE length does not match content length");

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        ext.encode(out2);
        check(Arrays.equals(enc1, out2.toByteArray()),
              "repeated encode produced different bytes");
    }
}
