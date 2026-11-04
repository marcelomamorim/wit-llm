/*
 * @test
 * @summary Verify CertificatePoliciesExtension.get(String) name matching and unknown-name handling
 * @modules java.base/sun.security.x509
 * @run main CertificatePoliciesExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.security.x509.CertificatePoliciesExtension;
import sun.security.x509.PolicyInformation;

public class CertificatePoliciesExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CertificatePoliciesExtension ext =
                new CertificatePoliciesExtension(new ArrayList<PolicyInformation>());

        List<PolicyInformation> policies = ext.get("policies");
        if (policies == null) {
            throw new RuntimeException("get(\"policies\") returned null");
        }
        if (!policies.isEmpty()) {
            throw new RuntimeException("expected empty policy list");
        }

        List<PolicyInformation> upper = ext.get("POLICIES");
        if (upper == null || !upper.isEmpty()) {
            throw new RuntimeException("case-insensitive match failed for POLICIES");
        }

        List<PolicyInformation> mixed = ext.get("PoLiCiEs");
        if (mixed == null || !mixed.isEmpty()) {
            throw new RuntimeException("case-insensitive match failed for PoLiCiEs");
        }

        expectIOException(ext, "unknown");
        expectIOException(ext, "");
        expectIOException(ext, "policy");
        expectIOException(ext, "CertificatePolicies");
    }

    static void expectIOException(CertificatePoliciesExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for name [" + name + "]");
    }
}
