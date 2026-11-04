/*
 * @test
 * @summary Verify IssuingDistributionPointExtension.delete(String) accepts known attribute names case-insensitively and rejects unknown names with IOException
 * @modules java.base/sun.security.x509
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import java.io.IOException;

import sun.security.x509.IssuingDistributionPointExtension;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteKnownNamesCaseInsensitive();
        testDeleteIsIdempotent();
        testDeleteUnknownNameThrowsIOException();
        testDeleteUnknownNameDoesNotAcceptPrefix();
    }

    static IssuingDistributionPointExtension newExtension() throws IOException {
        return new IssuingDistributionPointExtension(null, null, true, true, true, true);
    }

    static void testDeleteKnownNamesCaseInsensitive() throws Exception {
        IssuingDistributionPointExtension ext = newExtension();
        ext.delete(IssuingDistributionPointExtension.ONLY_USER_CERTS.toUpperCase());
        ext.delete("Only_CA_Certs");
        ext.delete(IssuingDistributionPointExtension.ONLY_ATTRIBUTE_CERTS.toUpperCase());
        ext.delete("Indirect_CRL");
        ext.delete("POINT");
        ext.delete("reasons");
    }

    static void testDeleteIsIdempotent() throws Exception {
        IssuingDistributionPointExtension ext = newExtension();
        ext.delete(IssuingDistributionPointExtension.ONLY_USER_CERTS);
        ext.delete(IssuingDistributionPointExtension.ONLY_USER_CERTS);
        ext.delete(IssuingDistributionPointExtension.POINT);
        ext.delete(IssuingDistributionPointExtension.POINT);
    }

    static void testDeleteUnknownNameThrowsIOException() throws Exception {
        IssuingDistributionPointExtension ext = newExtension();
        boolean thrown = false;
        try {
            ext.delete("unknown_attribute");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for unknown attribute name");
        }
    }

    static void testDeleteUnknownNameDoesNotAcceptPrefix() throws Exception {
        IssuingDistributionPointExtension ext = newExtension();
        boolean thrown = false;
        try {
            ext.delete("only_user");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for partial attribute name");
        }
    }
}
