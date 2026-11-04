/*
 * @test
 * @summary Verifies that CertificateFactory.getCertPathEncodings() returns a non-null iterator with the standard X.509 CertPath encodings and does not throw UnsupportedOperationException
 * @run main CertificateFactoryWitupTest
 */

import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CertificateFactoryWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        Iterator<String> first = cf.getCertPathEncodings();
        if (first == null) {
            throw new RuntimeException("getCertPathEncodings() returned null");
        }
        List<String> firstEncs = drain(first);

        if (!firstEncs.contains("PkiPath")) {
            throw new RuntimeException("Missing PkiPath encoding: " + firstEncs);
        }
        if (!firstEncs.contains("PKCS7")) {
            throw new RuntimeException("Missing PKCS7 encoding: " + firstEncs);
        }

        Iterator<String> second = cf.getCertPathEncodings();
        List<String> secondEncs = drain(second);
        if (!firstEncs.equals(secondEncs)) {
            throw new RuntimeException("Encodings differ between calls: "
                    + firstEncs + " vs " + secondEncs);
        }
    }

    private static List<String> drain(Iterator<String> it) {
        List<String> out = new ArrayList<>();
        while (it.hasNext()) {
            out.add(it.next());
        }
        return out;
    }
}
