/*
 * @test
 * @summary Verify CertificatePoliciesExtension.set accepts List values for the policies attribute and rejects invalid names or values with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificatePoliciesExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.security.x509.CertAttrSet;
import sun.security.x509.CertificatePoliciesExtension;

public class CertificatePoliciesExtensionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIOException(CertAttrSet<String> attr, String name, Object value) {
        try {
            attr.set(name, value);
            throw new RuntimeException("Expected IOException for name=" + name + " value=" + value);
        } catch (IOException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        CertificatePoliciesExtension ext = new CertificatePoliciesExtension(new ArrayList<>());
        CertAttrSet<String> attr = ext;

        attr.set(CertificatePoliciesExtension.POLICIES, new ArrayList<>());
        Object got = attr.get(CertificatePoliciesExtension.POLICIES);
        check(got instanceof List, "policies attribute should be a List after set");

        attr.set("Policies", new ArrayList<>());
        got = attr.get(CertificatePoliciesExtension.POLICIES);
        check(got instanceof List, "case-insensitive name should be accepted");

        expectIOException(attr, "unknownAttribute", new ArrayList<>());
        expectIOException(attr, CertificatePoliciesExtension.POLICIES, "notAList");
        expectIOException(attr, CertificatePoliciesExtension.POLICIES, null);
    }
}
