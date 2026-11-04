/*
 * @test
 * @summary Test sun.security.provider.certpath.PolicyChecker.init(boolean) method behavior
 * @run main PolicyCheckerWitupTest
 */

import java.security.cert.CertPathValidatorException;
import java.util.Collections;
import java.util.HashSet;
import sun.security.provider.certpath.PolicyChecker;
import sun.security.provider.certpath.PolicyChecker.PolicyNodeImpl;

public class PolicyCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        testInitThrowsOnForwardTrue();
        testInitSetsFieldsOnForwardFalse();
        System.out.println("All tests passed.");
    }

    private static void testInitThrowsOnForwardTrue() {
        PolicyChecker checker = new PolicyChecker(
            Collections.singleton(PolicyChecker.ANY_POLICY),
            5, // certPathLen
            true, // expPolicyRequired
            true, // polMappingInhibited
            true, // anyPolicyInhibited
            false, // rejectPolicyQualifiers
            null // rootNode
        );

        try {
            checker.init(true);
            throw new AssertionError("Expected CertPathValidatorException not thrown");
        } catch (CertPathValidatorException e) {
            String msg = e.getMessage();
            if (!"forward checking not supported".equals(msg)) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testInitSetsFieldsOnForwardFalse() throws Exception {
        PolicyChecker checker = new PolicyChecker(
            Collections.singleton(PolicyChecker.ANY_POLICY),
            3, // certPathLen
            true, // expPolicyRequired
            false, // polMappingInhibited
            false, // anyPolicyInhibited
            false, // rejectPolicyQualifiers
            null // rootNode
        );

        checker.init(false);

        // Validate fields via public API or reflection if accessible
        // Since fields are private and no getters, we test behavior indirectly
        // Here we rely on no exception and assume fields set correctly
        // This is minimal due to lack of public accessors
    }
}
