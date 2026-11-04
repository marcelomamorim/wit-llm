/*
 * @test
 * @summary Verifies DER encoding of AlgorithmId.derEncode for parameterless algorithm IDs
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main AlgorithmIdWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class AlgorithmIdWitupTest {

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    private static byte[] encode(AlgorithmId id) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        id.derEncode(out);
        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        // rsaEncryption 1.2.840.113549.1.1.1 with NULL parameters
        byte[] expectedRsa = new byte[] {
            0x30, 0x0D,
            0x06, 0x09, 0x2A, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xF7, 0x0D, 0x01, 0x01, 0x01,
            0x05, 0x00
        };
        AlgorithmId rsa = new AlgorithmId(new ObjectIdentifier("1.2.840.113549.1.1.1"));
        byte[] actualRsa = encode(rsa);
        check(Arrays.equals(expectedRsa, actualRsa),
              "unexpected encoding for rsaEncryption: " + Arrays.toString(actualRsa));

        // Encoding must be a valid DER SEQUENCE that parses back to the same OID
        DerValue dv = new DerValue(actualRsa);
        check(dv.tag == DerValue.tag_Sequence, "outer tag is not SEQUENCE");
        check(dv.data.available() == 13 || dv.data.available() == 0,
              "unexpected remaining data in parsed sequence");
        DerValue oidValue = dv.data.getDerValue();
        check(oidValue.tag == DerValue.tag_ObjectId, "first element is not OID");
        ObjectIdentifier parsed = oidValue.getOID();
        check(parsed.toString().equals("1.2.840.113549.1.1.1"),
              "parsed OID mismatch: " + parsed);
        DerValue nullValue = dv.data.getDerValue();
        check(nullValue.tag == DerValue.tag_Null, "second element is not NULL");

        // Second arc-heavy OID 2.16.840.1.101.3.4.2.1 (sha256) with NULL parameters
        byte[] expectedSha256 = new byte[] {
            0x30, 0x0D,
            0x06, 0x09, 0x60, (byte) 0x86, 0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x01,
            0x05, 0x00
        };
        AlgorithmId sha256 = new AlgorithmId(new ObjectIdentifier("2.16.840.1.101.3.4.2.1"));
        byte[] actualSha256 = encode(sha256);
        check(Arrays.equals(expectedSha256, actualSha256),
              "unexpected encoding for sha256: " + Arrays.toString(actualSha256));

        // Encoding must be deterministic across repeated calls
        check(Arrays.equals(actualRsa, encode(rsa)), "encoding not repeatable");

        // Different OIDs must produce different encodings
        check(!Arrays.equals(actualRsa, actualSha256), "distinct OIDs encoded identically");
    }
}
