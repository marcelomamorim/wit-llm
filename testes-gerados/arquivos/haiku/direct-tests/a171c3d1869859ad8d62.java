/*
 * @test
 * @summary Verifies CertificateVersion.get(String) returns the version for the "number" attribute (case-insensitive) and throws IOException for unrecognized names
 * @modules java.base/sun.security.x509
 * @run main CertificateVersionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateVersion def = new CertificateVersion();
        check(def.get("number").intValue() == CertificateVersion.V1,
              "default version should be V1");

        CertificateVersion v2 = new CertificateVersion(CertificateVersion.V2);
        check(v2.get("number").intValue() == CertificateVersion.V2,
              "V2 should be returned for 'number'");

        CertificateVersion v3 = new CertificateVersion(CertificateVersion.V3);
        check(v3.get("number").intValue() == 2,
              "V3 should return 2");

        check(v3.get("NUMBER").intValue() == 2,
              "attribute name lookup should be case-insensitive (upper)");
        check(v3.get("Number").intValue() == 2,
              "attribute name lookup should be case-insensitive (mixed)");

        expectIOException(v3, "version");
        expectIOException(v3, "foo");
        expectIOException(v3, "");
        expectIOException(v3, "number ");
    }

    private static void expectIOException(CertificateVersion cv, String name) {
        try {
            cv.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for attribute name: '" + name + "'");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
