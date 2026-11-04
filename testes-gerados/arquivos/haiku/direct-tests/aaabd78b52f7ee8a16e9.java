/*
 * @test
 * @summary Verifies CertificateFactory.getCertPathEncodings() returns a non-null iterator of String encodings, including the standard X.509 encodings, and is repeatable.
 * @run main CertificateFactoryWitupTest
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CertificateFactoryWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        List<String> first = collect(cf.getCertPathEncodings());

        if (!first.contains("PkiPath")) {
            throw new RuntimeException("Missing PkiPath encoding: " + first);
        }
        if (!first.contains("PKCS7")) {
            throw new RuntimeException("Missing PKCS7 encoding: " + first);
        }

        List<String> second = collect(cf.getCertPathEncodings());
        if (!first.equals(second)) {
            throw new RuntimeException("Encodings differ across calls: " + first + " vs " + second);
        }

        CertificateFactory cfProv = CertificateFactory.getInstance("X.509", "SUN");
        List<String> fromProvider = collect(cfProv.getCertPathEncodings());
        if (!fromProvider.contains("PkiPath") || !fromProvider.contains("PKCS7")) {
            throw new RuntimeException("Provider SUN encodings incomplete: " + fromProvider);
        }
    }

    private static List<String> collect(Iterator<String> it) {
        if (it == null) {
            throw new RuntimeException("getCertPathEncodings returned null");
        }
        List<String> out = new ArrayList<>();
        while (it.hasNext()) {
            String s = it.next();
            if (s == null) {
                throw new RuntimeException("Null encoding name in iterator");
            }
            out.add(s);
        }
        return out;
    }
}
