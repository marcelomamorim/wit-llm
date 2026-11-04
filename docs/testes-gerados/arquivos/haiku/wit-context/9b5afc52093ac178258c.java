/*
 * @test
 * @summary WITUP-derived checks for CertificatePoliciesExtension.set(String, Object) exception paths
 * @modules java.base/sun.security.x509
 * @run main CertificatePoliciesExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import sun.security.x509.CertificatePoliciesExtension;

public class CertificatePoliciesExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testAcceptsPoliciesNameWithEmptyList();
        testAcceptsPoliciesNameCaseInsensitive();
        testRejectsNonListValue();
        testRejectsNullValueForPolicies();
        testRejectsUnknownAttributeName();
    }

    static void testAcceptsPoliciesNameWithEmptyList() throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<>());
        ext.set("policies", new ArrayList<Object>());
    }

    static void testAcceptsPoliciesNameCaseInsensitive() throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<>());
        ext.set("POLICIES", new ArrayList<Object>());
    }

    static void testRejectsNonListValue() throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<>());
        expectIOException(ext, "policies", "not a list");
    }

    static void testRejectsNullValueForPolicies() throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<>());
        expectIOException(ext, "policies", null);
    }

    static void testRejectsUnknownAttributeName() throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<>());
        expectIOException(ext, "other", new ArrayList<Object>());
    }

    static void expectIOException(CertificatePoliciesExtension ext,
                                  String name, Object value) {
        try {
            ext.set(name, value);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name
                + " value=" + value);
    }
}
