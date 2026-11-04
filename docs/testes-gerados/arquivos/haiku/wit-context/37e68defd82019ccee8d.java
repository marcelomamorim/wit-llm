/*
 * @test
 * @summary Witup generated checks for InhibitAnyPolicyExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testInvalidSkipCertsRejected();
        testEncodeContainsInhibitAnyPolicyOid();
        testEncodeContainsSkipCertsValue();
        testEncodeIsDeterministic();
        testEncodeIsCritical();
    }

    static void testInvalidSkipCertsRejected() {
        try {
            new InhibitAnyPolicyExtension(-2);
            throw new RuntimeException("Expected IOException for skipCerts=-2");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testEncodeContainsInhibitAnyPolicyOid() throws Exception {
        byte[] out = encode(new InhibitAnyPolicyExtension(0));
        byte[] oid = {(byte)0x06, (byte)0x03, (byte)0x55, (byte)0x1D, (byte)0x36};
        if (!contains(out, oid)) {
            throw new RuntimeException("OID 2.5.29.54 not found in encoding");
        }
    }

    static void testEncodeContainsSkipCertsValue() throws Exception {
        byte[] out = encode(new InhibitAnyPolicyExtension(-1));
        byte[] maxInt = {(byte)0x02, (byte)0x04, (byte)0x7F, (byte)0xFF, (byte)0xFF, (byte)0xFF};
        if (!contains(out, maxInt)) {
            throw new RuntimeException("Unlimited depth (Integer.MAX_VALUE) not encoded");
        }
    }

    static void testEncodeIsDeterministic() throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(3);
        byte[] first = encode(ext);
        byte[] second = encode(ext);
        if (first.length == 0 || first[0] != (byte)0x30) {
            throw new RuntimeException("Encoding must start with SEQUENCE tag");
        }
        if (first.length != second.length) {
            throw new RuntimeException("Encoding length differs between calls");
        }
        for (int i = 0; i < first.length; i++) {
            if (first[i] != second[i]) {
                throw new RuntimeException("Encoding differs at index " + i);
            }
        }
    }

    static void testEncodeIsCritical() throws Exception {
        byte[] out = encode(new InhibitAnyPolicyExtension(0));
        byte[] critical = {(byte)0x01, (byte)0x01, (byte)0xFF};
        if (!contains(out, critical)) {
            throw new RuntimeException("Extension must be encoded as critical");
        }
    }

    static byte[] encode(InhibitAnyPolicyExtension ext) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ext.encode(bos);
        return bos.toByteArray();
    }

    static boolean contains(byte[] hay, byte[] needle) {
        outer:
        for (int i = 0; i + needle.length <= hay.length; i++) {
            for (int j = 0; j < needle.length; j++) {
                if (hay[i + j] != needle[j]) {
                    continue outer;
                }
            }
            return true;
        }
        return false;
    }
}
