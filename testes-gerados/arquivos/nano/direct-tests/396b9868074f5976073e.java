/* @test    @summary Test delete method of IssuingDistributionPointExtension class    @run main IssuingDistributionPointExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.IssuingDistributionPointExtension;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeletePoint();
        testDeleteIndirectCRL();
        testDeleteReasons();
        testDeleteOnlyUserCerts();
        testDeleteOnlyCACerts();
        testDeleteOnlyAttributeCerts();
        testDeleteUnrecognizedAttribute();
    }

    static void testDeletePoint() throws IOException {
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(Boolean.TRUE, null);
        ext.delete("point");
        // Expect distributionPoint to be null after delete
        assert ext.distributionPoint == null : "Failed to delete point";
    }

    static void testDeleteIndirectCRL() throws IOException {
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(Boolean.TRUE, null);
        ext.delete("indirect_crl");
        // Expect isIndirectCRL to be false after delete
        assert ext.isIndirectCRL == false : "Failed to delete indirect_crl";
    }

    static void testDeleteReasons() throws IOException {
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(Boolean.TRUE, null);
        ext.delete("reasons");
        // Expect revocationReasons to be null after delete
        assert ext.revocationReasons == null : "Failed to delete reasons";
    }

    static void testDeleteOnlyUserCerts() throws IOException {
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(Boolean.TRUE, null);
        ext.delete("only_user_certs");
        // Expect hasOnlyUserCerts to be false after delete
        assert ext.hasOnlyUserCerts == false : "Failed to delete only_user_certs";
    }

    static void testDeleteOnlyCACerts() throws IOException {
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(Boolean.TRUE, null);
        ext.delete("only_ca_certs");
        // Expect hasOnlyCACerts to be false after delete
        assert ext.hasOnlyCACerts == false : "Failed to delete only_ca_certs";
    }

    static void testDeleteOnlyAttributeCerts() throws IOException {
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(Boolean.TRUE, null);
        ext.delete("only_attribute_certs");
        // Expect hasOnlyAttributeCerts to be false after delete
        assert ext.hasOnlyAttributeCerts == false : "Failed to delete only_attribute_certs";
    }

    static void testDeleteUnrecognizedAttribute() {
        try {
            IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(Boolean.TRUE, null);
            ext.delete("unknown_attribute");
            assert false : "Expected IOException for unrecognized attribute";
        } catch (IOException e) {
            assert e.getMessage().contains("not recognized") : "Unexpected exception message";
        }
    }
}
