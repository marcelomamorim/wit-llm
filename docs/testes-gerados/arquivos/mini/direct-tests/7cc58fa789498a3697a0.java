/*
 * @test
 * @summary Test sun.security.x509.AlgorithmId.encode() method
 * @run main AlgorithmIdWitupTest
 */

import sun.security.x509.AlgorithmId;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;

public class AlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithOidOnly();
        testEncodeWithOidAndParams();
    }

    private static void testEncodeWithOidOnly() throws IOException {
        // Use a known OID (e.g., 1.2.840.113549.1.1.1 = rsaEncryption)
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 840, 113549, 1, 1, 1});
        AlgorithmId algId = new AlgorithmId(oid);

        byte[] encoded = algId.encode();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("encode() returned null or empty byte array");
        }

        // Basic check: encoded data should start with ASN.1 SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new RuntimeException("Encoded data does not start with ASN.1 SEQUENCE tag");
        }
    }

    private static void testEncodeWithOidAndParams() throws Exception {
        // Use a known OID and empty AlgorithmParameters
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 840, 113549, 1, 1, 5}); // sha1WithRSAEncryption

        AlgorithmParameters params = AlgorithmParameters.getInstance("SHA1");
        params.init(new byte[0]); // empty parameters

        AlgorithmId algId = new AlgorithmId(oid, params);

        byte[] encoded = algId.encode();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("encode() returned null or empty byte array with params");
        }

        // Basic check: encoded data should start with ASN.1 SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new RuntimeException("Encoded data with params does not start with ASN.1 SEQUENCE tag");
        }
    }
}
