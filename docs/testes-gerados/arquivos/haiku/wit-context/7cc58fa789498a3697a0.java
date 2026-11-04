/*
 * @test
 * @summary Basic observable behavior of AlgorithmId.encode() for a parameterless AlgorithmId
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main AlgorithmIdWitupTest
 */

import java.util.Arrays;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class AlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549.1.1.1");
        AlgorithmId id = new AlgorithmId(oid);

        byte[] enc = id.encode();
        check(enc != null, "encode() returned null");
        check(enc.length > 4, "encoding too short: " + enc.length);
        check((enc[0] & 0xFF) == 0x30, "expected outer SEQUENCE tag 0x30");
        check((enc[2] & 0xFF) == 0x06, "expected OID tag 0x06 at index 2");
        check((enc[3] & 0xFF) == 0x09, "expected OID length 9 at index 3");

        byte[] repeated = id.encode();
        check(Arrays.equals(enc, repeated), "encode() not stable across repeated calls");

        byte[] other = new AlgorithmId(new ObjectIdentifier("1.2.840.113549.1.1.1")).encode();
        check(Arrays.equals(enc, other), "equal OIDs produced different encodings");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
