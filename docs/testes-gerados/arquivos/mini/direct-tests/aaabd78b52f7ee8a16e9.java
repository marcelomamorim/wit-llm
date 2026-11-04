/*
 * @test
 * @summary Test CertificateFactory.getCertPathEncodings() returns non-null iterator
 * @run main CertificateFactoryWitupTest
 */

import java.security.cert.CertificateFactory;
import java.util.Iterator;

public class CertificateFactoryWitupTest {
    public static void main(String[] args) throws Exception {
        // Obtain a CertificateFactory instance for the standard "X.509" type
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        // Call getCertPathEncodings and verify it returns a non-null Iterator
        Iterator<String> encodings = cf.getCertPathEncodings();
        if (encodings == null) {
            throw new AssertionError("getCertPathEncodings() returned null");
        }

        // Verify that the iterator has at least one encoding
        if (!encodings.hasNext()) {
            throw new AssertionError("getCertPathEncodings() iterator is empty");
        }

        // Iterate through encodings and verify none are null
        while (encodings.hasNext()) {
            String encoding = encodings.next();
            if (encoding == null) {
                throw new AssertionError("getCertPathEncodings() returned null encoding");
            }
        }
    }
}
