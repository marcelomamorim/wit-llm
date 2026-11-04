/*
 * @test
 * @summary Verifies that X500Name.encode(DerOutputStream) emits a deterministic DER SEQUENCE and distinguishes different names
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main X500NameWitupTest
 */

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Org, C=US");

        DerOutputStream out1 = new DerOutputStream();
        name.encode(out1);
        byte[] b1 = out1.toByteArray();

        if (b1.length < 2) {
            throw new RuntimeException("Encoded name too short: " + b1.length);
        }
        if (b1[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("Expected SEQUENCE tag, got " + b1[0]);
        }
        int len = b1[1] & 0xff;
        if (len >= 0x80) {
            throw new RuntimeException("Unexpected long-form length in test data");
        }
        if (len != b1.length - 2) {
            throw new RuntimeException("Length mismatch: header=" + len
                    + " actual=" + (b1.length - 2));
        }

        DerOutputStream out2 = new DerOutputStream();
        name.encode(out2);
        byte[] b2 = out2.toByteArray();
        if (!sameBytes(b1, b2)) {
            throw new RuntimeException("Encoding is not deterministic");
        }

        X500Name other = new X500Name("CN=Other");
        DerOutputStream out3 = new DerOutputStream();
        other.encode(out3);
        byte[] b3 = out3.toByteArray();
        if (sameBytes(b1, b3)) {
            throw new RuntimeException("Different names produced identical encodings");
        }
        if (b3.length < 2 || b3[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("Second name lacks SEQUENCE tag");
        }
    }

    private static boolean sameBytes(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}
