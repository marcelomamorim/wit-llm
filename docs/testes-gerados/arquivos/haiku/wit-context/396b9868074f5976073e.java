/*
 * @test
 * @summary Witup-generated checks for IssuingDistributionPointExtension.delete(String)
 * @modules java.base/sun.security.x509
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import java.io.IOException;

import sun.security.x509.IssuingDistributionPointExtension;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        IssuingDistributionPointExtension ext =
                new IssuingDistributionPointExtension(null, null, false, false, false, true);

        ext.delete(IssuingDistributionPointExtension.INDIRECT_CRL);
        ext.delete("INDIRECT_CRL");
        ext.delete(IssuingDistributionPointExtension.POINT);
        ext.delete(IssuingDistributionPointExtension.REASONS);
        ext.delete(IssuingDistributionPointExtension.ONLY_USER_CERTS);
        ext.delete(IssuingDistributionPointExtension.ONLY_CA_CERTS);
        ext.delete(IssuingDistributionPointExtension.ONLY_ATTRIBUTE_CERTS);

        expectIOException(ext, "unknownAttribute");
        expectIOException(ext, "");
    }

    private static void expectIOException(IssuingDistributionPointExtension ext, String name) {
        try {
            ext.delete(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: [" + name + "]");
    }
}
