/*
 * @test
 * @summary Test sun.security.x509.AlgorithmId.derEncode(OutputStream) for normal and edge cases
 * @run main AlgorithmIdWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class AlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testDerEncodeWithValidOid();
        testDerEncodeWithNullParams();
    }

    private static void testDerEncodeWithValidOid() throws IOException {
        // Use a known OID (1.2.840.113549.1.1.1 = rsaEncryption) for testing
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 840, 113549, 1, 1, 1});
        AlgorithmId algId = new AlgorithmId(oid);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        algId.derEncode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("derEncode produced empty output");
        }

        // Basic sanity check: output should start with SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new RuntimeException("derEncode output does not start with SEQUENCE tag");
        }
    }

    private static void testDerEncodeWithNullParams() throws IOException {
        // Construct AlgorithmId with null AlgorithmParameters to force params == null
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 840, 113549, 1, 1, 1});
        AlgorithmId algId = new AlgorithmId(oid, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        algId.derEncode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("derEncode with null params produced empty output");
        }

        // Output should start with SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new RuntimeException("derEncode with null params output does not start with SEQUENCE tag");
        }
    }
}
