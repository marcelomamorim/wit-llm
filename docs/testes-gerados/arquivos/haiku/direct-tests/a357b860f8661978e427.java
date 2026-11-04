/*
 * @test
 * @summary Verify IssuingDistributionPointExtension.get returns scope flags, null optional fields, case-insensitive names and rejects unknown names
 * @modules java.base/sun.security.x509
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import java.io.IOException;

import sun.security.x509.IssuingDistributionPointExtension;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        IssuingDistributionPointExtension ext =
            new IssuingDistributionPointExtension(null, null, true, false, false, true);

        check(Boolean.TRUE.equals(ext.get(IssuingDistributionPointExtension.ONLY_USER_CERTS)),
              "only_user_certs should be TRUE");
        check(Boolean.FALSE.equals(ext.get(IssuingDistributionPointExtension.ONLY_CA_CERTS)),
              "only_ca_certs should be FALSE");
        check(Boolean.FALSE.equals(ext.get(IssuingDistributionPointExtension.ONLY_ATTRIBUTE_CERTS)),
              "only_attribute_certs should be FALSE");
        check(Boolean.TRUE.equals(ext.get(IssuingDistributionPointExtension.INDIRECT_CRL)),
              "indirect_crl should be TRUE");
        check(ext.get(IssuingDistributionPointExtension.POINT) == null,
              "point should be null when not set");
        check(ext.get(IssuingDistributionPointExtension.REASONS) == null,
              "reasons should be null when not set");

        check(Boolean.TRUE.equals(ext.get("ONLY_USER_CERTS")),
              "name lookup should be case-insensitive");
        check(Boolean.FALSE.equals(ext.get("Only_CA_Certs")),
              "mixed-case name should be accepted");

        boolean thrown = false;
        try {
            ext.get("no_such_attribute");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "unknown attribute name should throw IOException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
