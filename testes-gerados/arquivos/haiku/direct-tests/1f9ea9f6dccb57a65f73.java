/*
 * @test
 * @summary Verify CertificateAlgorithmId.get(String) name matching and unrecognized-name behavior
 * @modules java.base/sun.security.x509
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.IOException;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testAlgorithmNameReturnsStoredValue();
        testAlgorithmNameIsCaseInsensitive();
        testUnrecognizedNamesThrow();
    }

    static void testAlgorithmNameReturnsStoredValue() throws Exception {
        CertificateAlgorithmId cai = new CertificateAlgorithmId((AlgorithmId) null);
        AlgorithmId result = cai.get(CertificateAlgorithmId.ALGORITHM);
        if (result != null) {
            throw new RuntimeException("Expected null stored algId, got " + result);
        }
    }

    static void testAlgorithmNameIsCaseInsensitive() throws Exception {
        CertificateAlgorithmId cai = new CertificateAlgorithmId((AlgorithmId) null);
        String[] names = {"ALGORITHM", "Algorithm", "algorithm", "aLgOrItHm"};
        for (String name : names) {
            AlgorithmId result = cai.get(name);
            if (result != null) {
                throw new RuntimeException("Name " + name + " returned unexpected value");
            }
        }
    }

    static void testUnrecognizedNamesThrow() {
        CertificateAlgorithmId cai = new CertificateAlgorithmId((AlgorithmId) null);
        String[] names = {"", "bogus", "algorithmID", "x509.info.algorithmID"};
        for (String name : names) {
            expectIOException(cai, name);
        }
    }

    static void expectIOException(CertificateAlgorithmId cai, String name) {
        try {
            cai.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name: " + name);
    }
}
