/*
 * @test
 * @summary Verifies CertificateIssuerExtension.set validation of attribute names and value types
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerExtensionWitupTest
 */
import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.GeneralNames;

public class CertificateIssuerExtensionWitupTest {

    private static final String TYPE_MSG = "Attribute value must be of type GeneralNames";
    private static final String NAME_MSG = "Attribute name not recognized by CertAttrSet:CertificateIssuer";

    public static void main(String[] args) throws Exception {
        CertificateIssuerExtension ext = new CertificateIssuerExtension((GeneralNames) null);

        String m = expectIOException(ext, "issuer", (Object) null);
        check(TYPE_MSG.equals(m), "null value: " + m);

        m = expectIOException(ext, "issuer", "not-a-GeneralNames");
        check(TYPE_MSG.equals(m), "String value: " + m);

        m = expectIOException(ext, "ISSUER", Integer.valueOf(1));
        check(TYPE_MSG.equals(m), "case-insensitive wrong type: " + m);

        m = expectIOException(ext, "other", (Object) (GeneralNames) null);
        check(NAME_MSG.equals(m), "unknown name: " + m);

        m = expectIOException(ext, "", (Object) null);
        check(NAME_MSG.equals(m), "empty name: " + m);

        ext.set("issuer", (Object) (GeneralNames) null);
        ext.set("Issuer", (Object) (GeneralNames) null);
        ext.set("ISSUER", (Object) (GeneralNames) null);
    }

    private static String expectIOException(CertificateIssuerExtension ext, String name, Object obj) {
        try {
            ext.set(name, obj);
        } catch (IOException e) {
            return e.getMessage();
        }
        throw new RuntimeException("Expected IOException for name=" + name);
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Unexpected result: " + msg);
        }
    }
}
