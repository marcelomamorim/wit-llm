/*
 * @test
 * @summary PolicyInformation.delete must accept qualifiers case-insensitively and reject ID, unknown names and null names
 * @modules java.base/sun.security.x509
 * @run main PolicyInformationWitupTest
 */

import java.io.IOException;
import java.security.cert.PolicyQualifierInfo;
import java.util.Collections;
import java.util.Set;
import sun.security.x509.PolicyInformation;

public class PolicyInformationWitupTest {

    public static void main(String[] args) throws Exception {
        Set<PolicyQualifierInfo> empty = Collections.emptySet();
        PolicyInformation pi = new PolicyInformation(null, empty);

        pi.delete("qualifiers");
        pi.delete("QUALIFIERS");

        expectIOException(pi, "id");
        expectIOException(pi, "ID");
        expectIOException(pi, "unknown");
        expectIOException(pi, "");

        expectNullPointerException(pi, null);
    }

    static void expectIOException(PolicyInformation pi, String name) {
        try {
            pi.delete(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name: " + name);
    }

    static void expectNullPointerException(PolicyInformation pi, String name) {
        try {
            pi.delete(name);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Expected NullPointerException, got IOException", e);
        }
        throw new RuntimeException("Expected NullPointerException for null name");
    }
}
