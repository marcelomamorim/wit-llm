/*
 * @test
 * @summary Verifies DER encoding of AlgorithmId.derEncode for parameterless algorithm IDs
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main AlgorithmIdWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class AlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        check(new AlgorithmId(new ObjectIdentifier("1.2.3")),
              new byte[] {0x30, 0x06, 0x06, 0x02, 0x2A, 0x03, 0x05, 0x00});

        check(new AlgorithmId(new ObjectIdentifier("1.2.840.113549.1.1.1")),
              new byte[] {0x30, 0x0D, 0x06, 0x09, 0x2A, (byte) 0x86, 0x48,
                          (byte) 0x86, (byte) 0xF7, 0x0D, 0x01, 0x01, 0x01,
                          0x05, 0x00});

        AlgorithmId id = new AlgorithmId(new ObjectIdentifier("1.2.3"));
        ByteArrayOutputStream first = new ByteArrayOutputStream();
        ByteArrayOutputStream second = new ByteArrayOutputStream();
        id.derEncode(first);
        id.derEncode(second);
        if (!Arrays.equals(first.toByteArray(), second.toByteArray())) {
            throw new RuntimeException("derEncode is not deterministic");
        }
    }

    static void check(AlgorithmId id, byte[] expected) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        id.derEncode(out);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException("Unexpected DER encoding: "
                    + Arrays.toString(actual));
        }
    }
}
