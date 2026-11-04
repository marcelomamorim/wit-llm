/*
 * @test
 * @summary Test sun.security.x509.AlgorithmId.encode(DerOutputStream) method
 * @run main AlgorithmIdWitupTest
 */

import sun.security.x509.AlgorithmId;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;

public class AlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithOidOnly();
        testEncodeWithOidAndParams();
    }

    private static void testEncodeWithOidOnly() throws IOException {
        // Create AlgorithmId with a known OID and no parameters
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 840, 113549});
        AlgorithmId algId = new AlgorithmId(oid);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        // encode should not throw and produce some output
        algId.encode(derOut);
        byte[] encoded = baos.toByteArray();

        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeWithOidAndParams() throws Exception {
        // Create AlgorithmId with OID and dummy AlgorithmParameters
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 840, 113549});

        // Create empty AlgorithmParameters instance for "RAW" algorithm
        AlgorithmParameters params = AlgorithmParameters.getInstance("RAW");
        params.init(new byte[0]);

        AlgorithmId algId = new AlgorithmId(oid, params);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        // encode should not throw and produce some output
        algId.encode(derOut);
        byte[] encoded = baos.toByteArray();

        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }
}
