/*
 * @test
 * @summary Verify CertificatePoliciesExtension.delete accepts the policies attribute name case-insensitively and rejects unknown names with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificatePoliciesExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;

import sun.security.x509.CertificatePoliciesExtension;

public class CertificatePoliciesExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteExactPoliciesName();
        testDeleteCaseInsensitiveName();
        testDeleteRepeatedly();
        testDeleteUnknownNameThrows();
        testDeleteEmptyNameThrows();
    }

    static void testDeleteExactPoliciesName() throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<>());
        ext.delete(CertificatePoliciesExtension.POLICIES);
    }

    static void testDeleteCaseInsensitiveName() throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<>());
        ext.delete("POLICIES");
        ext.delete("Policies");
    }

    static void testDeleteRepeatedly() throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<>());
        ext.delete(CertificatePoliciesExtension.POLICIES);
        ext.delete(CertificatePoliciesExtension.POLICIES);
    }

    static void testDeleteUnknownNameThrows() throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<>());
        try {
            ext.delete("unknownAttribute");
            throw new RuntimeException("Expected IOException was not thrown");
        } catch (IOException expected) {
            // expected: attribute name not recognized
        }
    }

    static void testDeleteEmptyNameThrows() throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<>());
        try {
            ext.delete("");
            throw new RuntimeException("Expected IOException was not thrown");
        } catch (IOException expected) {
            // expected: empty name is not POLICIES
        }
    }
}
