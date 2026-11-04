/*
 * @test
 * @summary Checks observable DER output of IssuingDistributionPointExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.IssuingDistributionPointExtension;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeIsDeterministic();
        testEncodeStartsWithSequenceTag();
        testEncodeContainsIssuingDistributionPointOid();
        testScopeFlagChangesEncoding();
    }

    static IssuingDistributionPointExtension make(boolean onlyUserCerts) throws IOException {
        return new IssuingDistributionPointExtension(null, null, onlyUserCerts, false, false, false);
    }

    static byte[] encode(IssuingDistributionPointExtension ext) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ext.encode(bos);
        return bos.toByteArray();
    }

    static void testEncodeIsDeterministic() throws Exception {
        IssuingDistributionPointExtension ext = make(true);
        byte[] first = encode(ext);
        byte[] second = encode(ext);
        if (first.length == 0) {
            throw new RuntimeException("encode produced no bytes");
        }
        if (!java.util.Arrays.equals(first, second)) {
            throw new RuntimeException("repeated encode produced different bytes");
        }
    }

    static void testEncodeStartsWithSequenceTag() throws Exception {
        byte[] out = encode(make(false));
        if ((out[0] & 0xFF) != 0x30) {
            throw new RuntimeException("expected outer DER SEQUENCE tag 0x30, got " + (out[0] & 0xFF));
        }
    }

    static void testEncodeContainsIssuingDistributionPointOid() throws Exception {
        // DER OID 2.5.29.28 = 06 03 55 1D 1C
        byte[] oid = {0x06, 0x03, 0x55, 0x1D, 0x1C};
        byte[] out = encode(make(false));
        if (indexOf(out, oid) < 0) {
            throw new RuntimeException("encoding does not contain OID 2.5.29.28");
        }
    }

    static void testScopeFlagChangesEncoding() throws Exception {
        byte[] withFlag = encode(make(true));
        byte[] withoutFlag = encode(make(false));
        if (java.util.Arrays.equals(withFlag, withoutFlag)) {
            throw new RuntimeException("onlyUserCerts flag did not affect encoding");
        }
    }

    static int indexOf(byte[] haystack, byte[] needle) {
        outer:
        for (int i = 0; i + needle.length <= haystack.length; i++) {
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
