/*
 * @test
 * @summary Test sun.security.x509.AlgorithmId.derEncode(OutputStream) basic behavior
 * @run main AlgorithmIdWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.AlgorithmId;
import sun.security.util.ObjectIdentifier;
import java.security.AlgorithmParameters;

public class AlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testDerEncodeWithNullParams();
        testDerEncodeWithNonNullParams();
    }

    private static void testDerEncodeWithNullParams() throws IOException {
        // Create AlgorithmId with only ObjectIdentifier, params null
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 840, 113549});
        AlgorithmId algId = new AlgorithmId(oid);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        algId.derEncode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("derEncode output is empty");
        }

        // The encoding should start with SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new RuntimeException("derEncode output does not start with SEQUENCE tag");
        }
    }

    private static void testDerEncodeWithNonNullParams() throws Exception {
        // Create AlgorithmParameters instance for "SHA-256" (common algorithm)
        AlgorithmParameters params = AlgorithmParameters.getInstance("SHA-256");
        params.init(new byte[] {0x05, 0x00}); // DER encoding of NULL

        ObjectIdentifier oid = new ObjectIdentifier(new int[] {2, 16, 840, 1, 101, 3, 4, 2, 1}); // OID for SHA-256
        AlgorithmId algId = new AlgorithmId(oid, params);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        algId.derEncode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("derEncode output is empty");
        }

        // The encoding should start with SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new RuntimeException("derEncode output does not start with SEQUENCE tag");
        }
    }
}
