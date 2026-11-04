/* @test    @summary Test getCertPathEncodings returns an iterator over encodings  @run main CertificateFactoryWitupTest */

import java.security.cert.CertificateFactory;
import java.util.Iterator;

public class CertificateFactoryWitupTest {

    public static void main(String[] args) {
        testGetCertPathEncodings();
    }

    public static void testGetCertPathEncodings() {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Iterator<String> encodings = cf.getCertPathEncodings();
            boolean hasNext = encodings.hasNext();
            if (hasNext) {
                String encoding = encodings.next();
                System.out.println("Encoding: " + encoding);
            } else {
                System.out.println("No encodings available.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception during getCertPathEncodings test", e);
        }
    }
}
