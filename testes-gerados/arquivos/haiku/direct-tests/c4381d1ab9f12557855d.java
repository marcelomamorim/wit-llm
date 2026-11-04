/*
 * @test
 * @summary Verifies that AlgorithmId.encode(DerOutputStream) emits a DER SEQUENCE whose first element is the algorithm OID, deterministically and per OID.
 * @modules java.base/sun.security.util
 * @run main AlgorithmIdWitupTest
 */

import java.util.Arrays;

import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class AlgorithmIdWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static byte[] encode(AlgorithmId id) throws Exception {
        DerOutputStream out = new DerOutputStream();
        id.encode(out);
        return out.toByteArray();
    }

    private static String decodeOid(byte[] enc) throws Exception {
        DerValue seq = new DerValue(enc);
        check(seq.isSequence(), "encoded value is not a SEQUENCE");
        DerInputStream in = seq.getData();
        ObjectIdentifier decoded = in.getDerValue().getOID();
        return decoded.toString();
    }

    public static void main(String[] args) throws Exception {
        String rsaOid = "1.2.840.113549.1.1.1";
        AlgorithmId rsa = new AlgorithmId(new ObjectIdentifier(rsaOid));

        byte[] enc1 = encode(rsa);
        check(enc1.length > 0, "encoding is empty");
        check(rsaOid.equals(decodeOid(enc1)),
              "decoded OID does not match the constructed OID");

        byte[] enc2 = encode(rsa);
        check(Arrays.equals(enc1, enc2),
              "repeated encode calls produced different bytes");

        String sha256WithRsaOid = "1.2.840.113549.1.1.11";
        AlgorithmId other = new AlgorithmId(new ObjectIdentifier(sha256WithRsaOid));
        byte[] encOther = encode(other);
        check(sha256WithRsaOid.equals(decodeOid(encOther)),
              "decoded OID does not match the second constructed OID");
        check(!Arrays.equals(enc1, encOther),
              "different OIDs produced identical encodings");
    }
}
