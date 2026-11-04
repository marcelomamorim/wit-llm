/*
 * @test
 * @summary Checks that PolicyMappingsExtension.encode(OutputStream) emits a deterministic DER extension with the critical flag
 * @modules java.base/sun.security.x509
 * @run main PolicyMappingsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] emptySequence = {0x30, 0x00};
        PolicyMappingsExtension ext = new PolicyMappingsExtension(Boolean.TRUE, emptySequence);

        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        ext.encode(out1);
        byte[] enc1 = out1.toByteArray();

        if (enc1.length < 2) {
            throw new RuntimeException("Encoded extension too short: " + enc1.length);
        }
        if (enc1[0] != 0x30) {
            throw new RuntimeException("Encoded extension must start with SEQUENCE tag, got " + enc1[0]);
        }

        boolean criticalFound = false;
        for (int i = 0; i + 2 < enc1.length; i++) {
            if (enc1[i] == 0x01 && enc1[i + 1] == 0x01 && enc1[i + 2] == (byte) 0xFF) {
                criticalFound = true;
                break;
            }
        }
        if (!criticalFound) {
            throw new RuntimeException("Critical flag BOOLEAN TRUE not found in encoding");
        }

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        ext.encode(out2);
        byte[] enc2 = out2.toByteArray();
        if (!Arrays.equals(enc1, enc2)) {
            throw new RuntimeException("Repeated encode produced different bytes");
        }
    }
}
