/*
 * @test
 * @summary Checks CertificateValidity.delete accepts the known attribute names case-insensitively and rejects unknown names with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificateValidityWitupTest
 */

import java.io.IOException;
import java.util.Date;
import sun.security.x509.CertificateValidity;

public class CertificateValidityWitupTest {

    public static void main(String[] args) throws Exception {
        Date notBefore = new Date(1000000000000L);
        Date notAfter = new Date(1500000000000L);
        CertificateValidity cv = new CertificateValidity(notBefore, notAfter);

        cv.delete(CertificateValidity.NOT_BEFORE);
        cv.delete(CertificateValidity.NOT_BEFORE);
        cv.delete("NOTAFTER");
        cv.delete(CertificateValidity.NOT_AFTER);

        expectIOException(cv, "unknownAttribute");
        expectIOException(cv, "");
        expectIOException(cv, "notBeforeX");
    }

    private static void expectIOException(CertificateValidity cv, String name) {
        try {
            cv.delete(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: " + name);
    }
}
