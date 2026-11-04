/*
 * @test
 * @summary Checks public get(String) lookups of IssuingDistributionPointExtension, including case-insensitive names and the IOException for unrecognized names
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
        check(Boolean.TRUE.equals(ext.get("INDIRECT_CRL")),
                "indirect_crl lookup should be case-insensitive");
        check(ext.get(IssuingDistributionPointExtension.POINT) == null,
                "point should be null when no distribution point is given");
        check(ext.get(IssuingDistributionPointExtension.REASONS) == null,
                "reasons should be null when no reason flags are given");

        expectIOException(ext, "bogus");
        expectIOException(ext, "");
    }

    private static void expectIOException(IssuingDistributionPointExtension ext, String name) {
        try {
            Object value = ext.get(name);
            throw new RuntimeException("Expected IOException for name [" + name + "], got " + value);
        } catch (IOException expected) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
