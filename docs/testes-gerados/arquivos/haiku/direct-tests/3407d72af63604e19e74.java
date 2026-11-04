/*
 * @test
 * @summary Verifies CertificateValidity.delete accepts case-insensitive known names and rejects unknown names
 * @modules java.base/sun.security.x509
 * @run main CertificateValidityWitupTest
 */
import java.io.IOException;
import java.util.Date;
import sun.security.x509.CertificateValidity;

public class CertificateValidityWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateValidity cv = new CertificateValidity(new Date(0L), new Date(1000000L));

        cv.delete("notBefore");
        cv.delete("NOTBEFORE");
        cv.delete("notAfter");
        cv.delete("NotAfter");

        CertificateValidity empty = new CertificateValidity();
        empty.delete("notBefore");
        empty.delete("notAfter");

        expectIOException(cv, "bogus");
        expectIOException(cv, "");
        expectIOException(cv, "notBefore ");
        expectIOException(cv, "validity");

        boolean npe = false;
        try {
            cv.delete(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("expected NullPointerException for null name");
        }
    }

    private static void expectIOException(CertificateValidity cv, String name) {
        try {
            cv.delete(name);
        } catch (IOException e) {
            return;
        }
        throw new AssertionError("expected IOException for name: '" + name + "'");
    }
}
