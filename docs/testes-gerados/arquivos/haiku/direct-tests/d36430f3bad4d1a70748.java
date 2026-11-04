/*
 * @test
 * @summary Verifies that AVA.encode(DerOutputStream) emits the expected DER SEQUENCE { OID, value } encoding
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main AVAWitupTest
 */

import java.util.Arrays;

import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AVA;

public class AVAWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static byte[] encode(AVA ava) throws Exception {
        DerOutputStream out = new DerOutputStream();
        ava.encode(out);
        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        // CN (2.5.4.3) with PrintableString "abc"
        // SEQUENCE(0x30, len 10) { OID 06 03 55 04 03, PrintableString 13 03 61 62 63 }
        byte[] expected = new byte[] {
            0x30, 0x0a,
            0x06, 0x03, 0x55, 0x04, 0x03,
            0x13, 0x03, 0x61, 0x62, 0x63
        };

        ObjectIdentifier cn = new ObjectIdentifier("2.5.4.3");
        DerValue value = new DerValue(new byte[] {0x13, 0x03, 0x61, 0x62, 0x63});
        AVA ava = new AVA(cn, value);

        byte[] encoded = encode(ava);
        check(Arrays.equals(expected, encoded),
              "Unexpected DER encoding: " + Arrays.toString(encoded));

        // Encoding must be deterministic across repeated calls on the same instance
        byte[] encodedAgain = encode(ava);
        check(Arrays.equals(encoded, encodedAgain),
              "Repeated encode produced different output");

        // A different value must produce a different encoding
        DerValue other = new DerValue(new byte[] {0x13, 0x03, 0x78, 0x79, 0x7a});
        AVA avaOther = new AVA(cn, other);
        byte[] encodedOther = encode(avaOther);
        check(!Arrays.equals(encoded, encodedOther),
              "Different values encoded identically");
        check(encodedOther[0] == 0x30 && encodedOther[1] == 0x0a,
              "Unexpected outer SEQUENCE header for second value");
    }
}
