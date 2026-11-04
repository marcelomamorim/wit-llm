/*
 * @test
 * @summary Verify CertificateAlgorithmId.get(String) returns the stored value for the "algorithm" name (case-insensitive) and throws IOException for other names
 * @modules java.base/sun.security.x509
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.IOException;

import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateAlgorithmId id = new CertificateAlgorithmId((AlgorithmId) null);

        check(id.get("algorithm") == null, "get(\"algorithm\") should return stored null value");
        check(id.get("ALGORITHM") == null, "get(\"ALGORITHM\") should be case-insensitive");
        check(id.get("Algorithm") == null, "get(\"Algorithm\") should be case-insensitive");

        expectIOException(id, "other");
        expectIOException(id, "");
        expectIOException(id, "algorithmID");
        expectIOException(id, "algorithm ");
    }

    private static void expectIOException(CertificateAlgorithmId id, String name) {
        try {
            AlgorithmId result = id.get(name);
            throw new RuntimeException("Expected IOException for name \"" + name
                    + "\" but got " + result);
        } catch (IOException expected) {
            // expected for unrecognized attribute names
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
