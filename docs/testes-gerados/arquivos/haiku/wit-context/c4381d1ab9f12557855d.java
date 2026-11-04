/*
 * @test
 * @summary Checks that AlgorithmId.encode(DerOutputStream) delegates to derEncode and produces a deterministic DER SEQUENCE
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main AlgorithmIdWitupTest
 */

import java.util.Arrays;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class AlgorithmIdWitupTest {

    private static final byte TAG_SEQUENCE = 0x30;

    public static void main(String[] args) throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549.1.1.1");
        AlgorithmId alg = new AlgorithmId(oid);

        DerOutputStream viaEncode = new DerOutputStream();
        alg.encode(viaEncode);
        byte[] encoded = viaEncode.toByteArray();

        if (encoded.length == 0) {
            throw new RuntimeException("encode produced no bytes");
        }
        if (encoded[0] != TAG_SEQUENCE) {
            throw new RuntimeException("encode output does not start with SEQUENCE tag");
        }

        DerOutputStream viaDer = new DerOutputStream();
        alg.derEncode(viaDer);
        if (!Arrays.equals(encoded, viaDer.toByteArray())) {
            throw new RuntimeException("encode output differs from derEncode output");
        }

        DerOutputStream second = new DerOutputStream();
        alg.encode(second);
        if (!Arrays.equals(encoded, second.toByteArray())) {
            throw new RuntimeException("encode is not deterministic for the same instance");
        }

        DerOutputStream otherAlg = new DerOutputStream();
        new AlgorithmId(new ObjectIdentifier("1.2.840.113549.1.1.5")).encode(otherAlg);
        if (Arrays.equals(encoded, otherAlg.toByteArray())) {
            throw new RuntimeException("different OIDs produced identical encodings");
        }
    }
}
