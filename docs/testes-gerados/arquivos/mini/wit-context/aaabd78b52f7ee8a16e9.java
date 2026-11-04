/*
 * @test
 * @summary Test CertificateFactory.getCertPathEncodings() throws UnsupportedOperationException
 * @run main CertificateFactoryWitupTest
 */

import java.security.cert.CertificateFactory;
import java.util.Iterator;

public class CertificateFactoryWitupTest {
    public static void main(String[] args) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        try {
            Iterator<String> it = cf.getCertPathEncodings();
            // If no exception, fail test
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected path: test passes
        }
    }
}
