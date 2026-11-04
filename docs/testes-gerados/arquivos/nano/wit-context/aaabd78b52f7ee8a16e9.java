/* @test    @summary Test getCertPathEncodings method for normal behavior and exception handling    @run main CertificateFactoryWitupTest */

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
            if (encodings != null) {
                System.out.println("getCertPathEncodings returned an iterator.");
                // Optionally, iterate and print encodings
                while (encodings.hasNext()) {
                    System.out.println("Encoding: " + encodings.next());
                }
            } else {
                System.out.println("getCertPathEncodings returned null.");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            e.printStackTrace();
        }
    }
}
