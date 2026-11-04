/*
 * @test
 * @summary WITUP-derived checks for CertificatePoliciesExtension.delete(String)
 * @modules java.base/sun.security.x509
 * @run main CertificatePoliciesExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.security.x509.CertificatePoliciesExtension;

public class CertificatePoliciesExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        deleteKnownNameIgnoringCaseSucceeds();
        deleteUnknownNameThrowsIOException();
        deleteUnknownNameDoesNotAcceptPartialMatch();
        deleteKnownNameIsIdempotent();
    }

    private static CertificatePoliciesExtension newEmptyExtension() throws IOException {
        List<?> policies = new ArrayList<>();
        return new CertificatePoliciesExtension(
                (List<sun.security.x509.PolicyInformation>) policies);
    }

    private static void deleteKnownNameIgnoringCaseSucceeds() throws Exception {
        String[] names = {"policies", "POLICIES", "Policies"};
        for (String name : names) {
            CertificatePoliciesExtension ext = newEmptyExtension();
            ext.delete(name);
        }
    }

    private static void deleteUnknownNameThrowsIOException() throws Exception {
        CertificatePoliciesExtension ext = newEmptyExtension();
        boolean thrown = false;
        try {
            ext.delete("unknownAttribute");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        }
    }

    private static void deleteUnknownNameDoesNotAcceptPartialMatch() throws Exception {
        String[] names = {"polic", "policiesX", " policies"};
        for (String name : names) {
            CertificatePoliciesExtension ext = newEmptyExtension();
            boolean thrown = false;
            try {
                ext.delete(name);
            } catch (IOException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IOException for name: [" + name + "]");
            }
        }
    }

    private static void deleteKnownNameIsIdempotent() throws Exception {
        CertificatePoliciesExtension ext = newEmptyExtension();
        ext.delete("policies");
        ext.delete("policies");
    }
}
