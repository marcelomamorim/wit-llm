/*
 * @test
 * @summary Checks NameConstraintsExtension.encode(OutputStream) output for an extension built from a DER value
 * @modules java.base/sun.security.x509
 * @run main NameConstraintsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import sun.security.x509.NameConstraintsExtension;

public class NameConstraintsExtensionWitupTest {

    private static final byte[] EMPTY_SEQ = {0x30, 0x00};
    private static final byte[] NC_OID_BYTES = {0x06, 0x03, 0x55, 0x1D, 0x1E};
    private static final byte[] CRITICAL_TRUE = {0x01, 0x01, (byte) 0xFF};

    public static void main(String[] args) throws Exception {
        NameConstraintsExtension critical =
                new NameConstraintsExtension(Boolean.TRUE, EMPTY_SEQ.clone());
        byte[] enc1 = encode(critical);
        byte[] enc2 = encode(critical);

        check(enc1.length > 0 && (enc1[0] & 0xFF) == 0x30, "outer SEQUENCE expected");
        check(contains(enc1, NC_OID_BYTES), "NameConstraints OID expected");
        check(contains(enc1, CRITICAL_TRUE), "critical BOOLEAN TRUE expected");
        check(Arrays.equals(enc1, enc2), "repeated encoding must be identical");

        NameConstraintsExtension nonCritical =
                new NameConstraintsExtension(Boolean.FALSE, EMPTY_SEQ.clone());
        byte[] enc3 = encode(nonCritical);
        check(contains(enc3, NC_OID_BYTES), "non-critical OID expected");
        check(!contains(enc3, CRITICAL_TRUE), "non-critical must not encode BOOLEAN TRUE");
    }

    private static byte[] encode(NameConstraintsExtension ext) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream out = bos;
        ext.encode(out);
        return bos.toByteArray();
    }

    private static boolean contains(byte[] data, byte[] pattern) {
        for (int i = 0; i + pattern.length <= data.length; i++) {
            boolean match = true;
            for (int j = 0; j < pattern.length; j++) {
                if (data[i + j] != pattern[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return true;
            }
        }
        return false;
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
