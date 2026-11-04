/*
 * @test
 * @summary Witup-generated checks for CRLReasonCodeExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testNonCriticalEncodesEnumerated();
        testCriticalEncodesBooleanAndEnumerated();
        testEncodeIsRepeatable();
    }

    static byte[] encode(CRLReasonCodeExtension ext) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ext.encode(out);
        return out.toByteArray();
    }

    static boolean contains(byte[] data, byte... pattern) {
        outer:
        for (int i = 0; i + pattern.length <= data.length; i++) {
            for (int j = 0; j < pattern.length; j++) {
                if (data[i + j] != pattern[j]) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }

    static void testNonCriticalEncodesEnumerated() throws Exception {
        byte[] der = encode(new CRLReasonCodeExtension(1));
        if (der.length == 0 || der[0] != 0x30) {
            throw new RuntimeException();
        }
        if (!contains(der, (byte) 0x04, (byte) 0x03, (byte) 0x0A, (byte) 0x01, (byte) 0x01)) {
            throw new RuntimeException();
        }
        if (contains(der, (byte) 0x01, (byte) 0x01, (byte) 0xFF)) {
            throw new RuntimeException();
        }
    }

    static void testCriticalEncodesBooleanAndEnumerated() throws Exception {
        byte[] der = encode(new CRLReasonCodeExtension(true, 1));
        if (!contains(der, (byte) 0x01, (byte) 0x01, (byte) 0xFF)) {
            throw new RuntimeException();
        }
        if (!contains(der, (byte) 0x04, (byte) 0x03, (byte) 0x0A, (byte) 0x01, (byte) 0x01)) {
            throw new RuntimeException();
        }
    }

    static void testEncodeIsRepeatable() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(false, 2);
        byte[] first = encode(ext);
        byte[] second = encode(ext);
        if (!java.util.Arrays.equals(first, second)) {
            throw new RuntimeException();
        }
    }
}
