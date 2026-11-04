/*
 * @test
 * @summary Checks deterministic and observable encoding of CRLReasonCodeExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] nonCritical1 = encode(new CRLReasonCodeExtension(1));
        byte[] nonCritical1Again = encode(new CRLReasonCodeExtension(1));

        check(nonCritical1.length > 0, "encoded output must not be empty");
        check(nonCritical1[0] == 0x30, "encoded extension must start with a DER SEQUENCE tag");
        check(Arrays.equals(nonCritical1, nonCritical1Again), "encoding must be deterministic");

        byte[] critical1 = encode(new CRLReasonCodeExtension(true, 1));
        check(!Arrays.equals(nonCritical1, critical1),
              "critical flag must change the encoded extension");

        byte[] reason2 = encode(new CRLReasonCodeExtension(2));
        check(!Arrays.equals(nonCritical1, reason2),
              "different reason codes must produce different encodings");

        // DER ENUMERATED(1) is 0x0A 0x01 0x01
        byte[] fromValue = encode(new CRLReasonCodeExtension(Boolean.FALSE,
                new byte[] {0x0A, 0x01, 0x01}));
        check(Arrays.equals(nonCritical1, fromValue),
              "constructor from DER value must encode the same as constructor from reason");
    }

    private static byte[] encode(CRLReasonCodeExtension ext) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ext.encode(out);
        return out.toByteArray();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
