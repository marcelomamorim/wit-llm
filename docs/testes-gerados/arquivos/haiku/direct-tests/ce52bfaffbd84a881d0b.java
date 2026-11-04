/*
 * @test
 * @summary Verify PolicyInformation.delete handles qualifiers, ID and unknown attribute names
 * @modules java.base/sun.security.x509
 * @run main PolicyInformationWitupTest
 */

import java.io.IOException;
import java.security.cert.PolicyQualifierInfo;
import java.util.HashSet;
import java.util.Set;
import sun.security.x509.PolicyInformation;

public class PolicyInformationWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorRejectsNullQualifiers();
        testDeleteQualifiersDoesNotThrow();
        testDeleteQualifiersIsCaseInsensitive();
        testDeleteIdThrowsIOException();
        testDeleteUnknownNameThrowsIOException();
        testObjectUsableAfterFailedDelete();
    }

    static void testConstructorRejectsNullQualifiers() throws Exception {
        try {
            new PolicyInformation(null, null);
            throw new RuntimeException("NullPointerException expected");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testDeleteQualifiersDoesNotThrow() throws Exception {
        Set<PolicyQualifierInfo> q = new HashSet<>();
        PolicyInformation pi = new PolicyInformation(null, q);
        pi.delete(PolicyInformation.QUALIFIERS);
    }

    static void testDeleteQualifiersIsCaseInsensitive() throws Exception {
        Set<PolicyQualifierInfo> q = new HashSet<>();
        PolicyInformation pi = new PolicyInformation(null, q);
        pi.delete("QuAlIfIeRs");
    }

    static void testDeleteIdThrowsIOException() throws Exception {
        Set<PolicyQualifierInfo> q = new HashSet<>();
        PolicyInformation pi = new PolicyInformation(null, q);
        expectIOException(pi, PolicyInformation.ID);
        expectIOException(pi, "Id");
        expectIOException(pi, "ID");
    }

    static void testDeleteUnknownNameThrowsIOException() throws Exception {
        Set<PolicyQualifierInfo> q = new HashSet<>();
        PolicyInformation pi = new PolicyInformation(null, q);
        expectIOException(pi, "unknown");
        expectIOException(pi, "");
        expectIOException(pi, "qualifier");
    }

    static void testObjectUsableAfterFailedDelete() throws Exception {
        Set<PolicyQualifierInfo> q = new HashSet<>();
        PolicyInformation pi = new PolicyInformation(null, q);
        expectIOException(pi, PolicyInformation.ID);
        pi.delete(PolicyInformation.QUALIFIERS);
    }

    static void expectIOException(PolicyInformation pi, String name) {
        try {
            pi.delete(name);
            throw new RuntimeException("IOException expected for name: " + name);
        } catch (IOException e) {
            // expected
        }
    }
}
