/*
 * @test
 * @summary Checks DER output of ExtendedKeyUsageExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Vector;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {

    static final byte[] EXT_KEY_USAGE_OID = {0x06, 0x03, 0x55, 0x1D, 0x25};
    static final byte[] SERVER_AUTH_OID = {0x06, 0x08, 0x2B, 0x06, 0x01, 0x05, 0x05, 0x07, 0x03, 0x01};
    static final byte[] CRITICAL_TRUE = {0x01, 0x01, (byte) 0xFF};

    public static void main(String[] args) throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1"));

        ExtendedKeyUsageExtension critical = new ExtendedKeyUsageExtension(Boolean.TRUE, usages);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        critical.encode(out);
        byte[] enc = out.toByteArray();

        check(enc.length > 0, "encoded output must not be empty");
        check((enc[0] & 0xFF) == 0x30, "outer element must be a SEQUENCE");
        check(contains(enc, EXT_KEY_USAGE_OID), "extension OID must be encoded");
        check(contains(enc, CRITICAL_TRUE), "critical flag TRUE must be encoded");
        check(contains(enc, SERVER_AUTH_OID), "key purpose OID must be encoded");

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        critical.encode(out2);
        check(Arrays.equals(enc, out2.toByteArray()), "repeated encode must be deterministic");

        ExtendedKeyUsageExtension nonCritical = new ExtendedKeyUsageExtension(usages);
        ByteArrayOutputStream out3 = new ByteArrayOutputStream();
        nonCritical.encode(out3);
        byte[] enc3 = out3.toByteArray();

        check((enc3[0] & 0xFF) == 0x30, "non-critical outer element must be a SEQUENCE");
        check(contains(enc3, EXT_KEY_USAGE_OID), "non-critical extension OID must be encoded");
        check(!contains(enc3, CRITICAL_TRUE), "non-critical extension must not encode critical TRUE");
        check(contains(enc3, SERVER_AUTH_OID), "non-critical key purpose OID must be encoded");
    }

    static boolean contains(byte[] data, byte[] pattern) {
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

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
