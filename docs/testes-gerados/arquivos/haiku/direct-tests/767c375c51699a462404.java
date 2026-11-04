/*
 * @test
 * @summary Verify IssuerAlternativeNameExtension.delete behavior for the recognized name, case-insensitive matching and unrecognized names
 * @modules java.base/sun.security.x509
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.DNSName;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNames;
import sun.security.x509.IssuerAlternativeNameExtension;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteIssuerNameClearsValue();
        testDeleteIsCaseInsensitive();
        testDeleteUnknownNameThrows();
    }

    static GeneralNames sampleNames() throws IOException {
        GeneralNames gn = new GeneralNames();
        gn.add(new GeneralName(new DNSName("example.com")));
        return gn;
    }

    static void testDeleteIssuerNameClearsValue() throws Exception {
        IssuerAlternativeNameExtension ext =
            new IssuerAlternativeNameExtension(sampleNames());
        if (ext.getExtensionValue() == null) {
            throw new RuntimeException("Expected encoded value before delete");
        }
        ext.delete(IssuerAlternativeNameExtension.ISSUER_NAME);
        if (ext.getExtensionValue() != null) {
            throw new RuntimeException("Expected null value after delete");
        }
    }

    static void testDeleteIsCaseInsensitive() throws Exception {
        IssuerAlternativeNameExtension ext =
            new IssuerAlternativeNameExtension(sampleNames());
        ext.delete("ISSUER_NAME");
        if (ext.getExtensionValue() != null) {
            throw new RuntimeException("Expected null value after case-insensitive delete");
        }
    }

    static void testDeleteUnknownNameThrows() throws Exception {
        IssuerAlternativeNameExtension ext =
            new IssuerAlternativeNameExtension(sampleNames());
        byte[] before = ext.getExtensionValue();
        try {
            ext.delete("unknown_attribute");
            throw new RuntimeException("Expected IOException for unrecognized name");
        } catch (IOException expected) {
            // expected
        }
        byte[] after = ext.getExtensionValue();
        if (after == null || before.length != after.length) {
            throw new RuntimeException("Value must be unchanged after failed delete");
        }
    }
}
