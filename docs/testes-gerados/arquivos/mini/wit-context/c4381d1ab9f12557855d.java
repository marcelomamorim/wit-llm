/*
 * @test
 * @summary Test sun.security.x509.AlgorithmId.encode(DerOutputStream) for basic encoding and exception safety
 * @run main AlgorithmIdWitupTest
 */

import sun.security.x509.AlgorithmId;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidOid();
        testEncodeWithNullDerOutputStream();
    }

    private static void testEncodeWithValidOid() throws IOException {
        // Use a known OID (e.g., 1.2.840.113549) which is a common OID prefix
        int[] oidInts = {1, 2, 840, 113549};
        ObjectIdentifier oid = new ObjectIdentifier(oidInts);
        AlgorithmId algId = new AlgorithmId(oid);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        // Should encode without throwing
        algId.encode(derOut);

        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeWithNullDerOutputStream() {
        // According to the source, encode calls derEncode(out), which will
        // likely throw NullPointerException if out is null.
        AlgorithmId algId = new AlgorithmId(new ObjectIdentifier(new int[]{1, 2, 3}));
        try {
            algId.encode(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException e) {
            // IOException is declared, but NPE is expected here for null argument
            throw new RuntimeException("Unexpected IOException instead of NPE", e);
        }
    }
}
