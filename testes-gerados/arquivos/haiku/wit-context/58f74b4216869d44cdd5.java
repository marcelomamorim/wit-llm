/*
 * @test
 * @summary Checks public get(String) behavior and constructor null checks of PolicyInformation
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
        Set<PolicyQualifierInfo> empty = new HashSet<PolicyQualifierInfo>();
        PolicyInformation pi = new PolicyInformation(null, empty);

        check(pi.get(PolicyInformation.ID) == null, "id lookup should return stored identifier (null)");
        check(pi.get("ID") == null, "id lookup must be case-insensitive");

        Object q = pi.get(PolicyInformation.QUALIFIERS);
        check(q instanceof Set, "qualifiers lookup should return a Set");
        check(((Set<?>) q).isEmpty(), "empty qualifier set must stay empty");
        check(pi.get("QUALIFIERS") instanceof Set, "qualifiers lookup must be case-insensitive");

        try {
            pi.get("unknownAttribute");
            throw new RuntimeException("unknown attribute must throw IOException");
        } catch (IOException expected) {
        }

        try {
            new PolicyInformation(null, null);
            throw new RuntimeException("null qualifiers must throw NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
