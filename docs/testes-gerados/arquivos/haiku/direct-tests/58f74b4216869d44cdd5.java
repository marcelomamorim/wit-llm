/*
 * @test
 * @summary Verifies PolicyInformation.get(String) lookup, case-insensitivity, and error handling
 * @modules java.base/sun.security.x509
 * @run main PolicyInformationWitupTest
 */

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.security.cert.PolicyQualifierInfo;
import sun.security.x509.PolicyInformation;

public class PolicyInformationWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testIdReturnsStoredIdentifier() throws Exception {
        PolicyInformation pi = new PolicyInformation(null, Collections.emptySet());
        check(pi.get(PolicyInformation.ID) == null, "get(ID) should return stored identifier (null)");
    }

    static void testQualifiersReturnsSet() throws Exception {
        Set<PolicyQualifierInfo> empty = Collections.emptySet();
        PolicyInformation pi = new PolicyInformation(null, empty);
        Object q = pi.get(PolicyInformation.QUALIFIERS);
        check(q instanceof Set, "get(QUALIFIERS) should return a Set");
        check(((Set<?>) q).isEmpty(), "qualifiers should be empty");
    }

    static void testCaseInsensitiveLookup() throws Exception {
        PolicyInformation pi = new PolicyInformation(null, Collections.emptySet());
        check(pi.get("ID") == pi.get("id"), "ID lookup should be case-insensitive");
        check(pi.get("Id") == pi.get(PolicyInformation.ID), "Id lookup should be case-insensitive");
        Object q1 = pi.get("QUALIFIERS");
        Object q2 = pi.get("qualifiers");
        check(q1 instanceof Set && q2 instanceof Set, "qualifier lookup should return Set regardless of case");
        check(((Set<?>) q1).equals(((Set<?>) q2)), "qualifier lookups should be equal");
    }

    static void testUnknownNameThrowsIOException() throws Exception {
        PolicyInformation pi = new PolicyInformation(null, Collections.emptySet());
        try {
            pi.get("unknown");
            throw new RuntimeException("expected IOException for unknown attribute");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testNameConstantNotAttribute() throws Exception {
        PolicyInformation pi = new PolicyInformation(null, Collections.emptySet());
        try {
            pi.get(PolicyInformation.NAME);
            throw new RuntimeException("expected IOException for NAME attribute");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testNullQualifiersRejected() throws Exception {
        try {
            new PolicyInformation(null, null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        testIdReturnsStoredIdentifier();
        testQualifiersReturnsSet();
        testCaseInsensitiveLookup();
        testUnknownNameThrowsIOException();
        testNameConstantNotAttribute();
        testNullQualifiersRejected();
    }
}
