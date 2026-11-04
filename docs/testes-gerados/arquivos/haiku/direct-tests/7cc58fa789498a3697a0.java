/*
 * @test
 * @summary Verifies observable DER output of AlgorithmId.encode()
 * @modules java.base/sun.security.util
 * @run main AlgorithmIdWitupTest
 */

import java.io.IOException;
import java.util.Arrays;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class AlgorithmIdWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static boolean containsSequence(byte[] data, byte[] pattern) {
        for (int i = 0; i + pattern.length <= data.length; i++) {
            boolean match = true;
            for (int j = 0; j < pattern.length; j++) {
                if (data[i + j] != pattern[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return true;
            }
        }
        return false;
    }

    static void testEncodeIsDerSequenceWithOid() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.4.3");
        AlgorithmId id = new AlgorithmId(oid);
        byte[] enc = id.encode();
        check(enc != null, "encode returned null");
        check(enc.length >= 2, "encoding too short");
        check((enc[0] & 0xff) == 0x30, "outer tag is not SEQUENCE");
        check((enc[1] & 0xff) == enc.length - 2, "outer length mismatch");
        byte[] oidDer = {0x06, 0x03, 0x55, 0x04, 0x03};
        check(containsSequence(enc, oidDer), "OID DER not found in encoding");
    }

    static void testEncodeIsDeterministic() throws IOException {
        AlgorithmId id = new AlgorithmId(new ObjectIdentifier("2.5.4.3"));
        byte[] first = id.encode();
        byte[] second = id.encode();
        check(Arrays.equals(first, second), "repeated encode differs");
    }

    static void testEncodeReturnsIndependentArray() throws IOException {
        AlgorithmId id = new AlgorithmId(new ObjectIdentifier("2.5.4.3"));
        byte[] first = id.encode();
        byte[] copy = first.clone();
        Arrays.fill(first, (byte) 0);
        byte[] second = id.encode();
        check(Arrays.equals(copy, second), "encode result was mutated by caller");
    }

    static void testDifferentOidsEncodeDifferently() throws IOException {
        byte[] a = new AlgorithmId(new ObjectIdentifier("2.5.4.3")).encode();
        byte[] b = new AlgorithmId(new ObjectIdentifier("2.5.4.6")).encode();
        check(!Arrays.equals(a, b), "different OIDs produced identical encodings");
    }

    public static void main(String[] args) throws Exception {
        testEncodeIsDerSequenceWithOid();
        testEncodeIsDeterministic();
        testEncodeReturnsIndependentArray();
        testDifferentOidsEncodeDifferently();
    }
}
