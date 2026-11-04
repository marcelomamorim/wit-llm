/*
 * @test
 * @summary Test PolicyChecker.init(boolean) method behavior
 * @run main PolicyCheckerWitupTest
 */

import java.security.cert.CertPathValidatorException;
import java.util.Collections;
import java.util.HashSet;
import sun.security.provider.certpath.PolicyChecker;
import sun.security.provider.certpath.PolicyNodeImpl;

public class PolicyCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        testInitForwardTrueThrows();
        testInitForwardFalseSetsFields();
    }

    private static void testInitForwardTrueThrows() {
        PolicyChecker checker = new PolicyChecker(
            Collections.singleton(PolicyChecker.ANY_POLICY),
            3, // certPathLen
            true, // expPolicyRequired
            true, // polMappingInhibited
            true, // anyPolicyInhibited
            false, // rejectPolicyQualifiers
            null // rootNode
        );
        try {
            checker.init(true);
            throw new RuntimeException("Expected CertPathValidatorException not thrown");
        } catch (CertPathValidatorException e) {
            if (!"forward checking not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testInitForwardFalseSetsFields() throws Exception {
        // Setup with various flags
        PolicyChecker checker = new PolicyChecker(
            Collections.singleton(PolicyChecker.ANY_POLICY),
            5, // certPathLen
            true, // expPolicyRequired
            false, // polMappingInhibited
            true, // anyPolicyInhibited
            false, // rejectPolicyQualifiers
            null // rootNode
        );

        checker.init(false);

        // Use reflection to verify private fields since no public getters
        java.lang.reflect.Field certIndexField = PolicyChecker.class.getDeclaredField("certIndex");
        certIndexField.setAccessible(true);
        int certIndex = certIndexField.getInt(checker);
        if (certIndex != 1) {
            throw new RuntimeException("certIndex expected 1 but was " + certIndex);
        }

        java.lang.reflect.Field explicitPolicyField = PolicyChecker.class.getDeclaredField("explicitPolicy");
        explicitPolicyField.setAccessible(true);
        int explicitPolicy = explicitPolicyField.getInt(checker);
        // expPolicyRequired true => explicitPolicy = 0
        if (explicitPolicy != 0) {
            throw new RuntimeException("explicitPolicy expected 0 but was " + explicitPolicy);
        }

        java.lang.reflect.Field policyMappingField = PolicyChecker.class.getDeclaredField("policyMapping");
        policyMappingField.setAccessible(true);
        int policyMapping = policyMappingField.getInt(checker);
        // polMappingInhibited false => policyMapping = certPathLen + 1 = 6
        if (policyMapping != 6) {
            throw new RuntimeException("policyMapping expected 6 but was " + policyMapping);
        }

        java.lang.reflect.Field inhibitAnyPolicyField = PolicyChecker.class.getDeclaredField("inhibitAnyPolicy");
        inhibitAnyPolicyField.setAccessible(true);
        int inhibitAnyPolicy = inhibitAnyPolicyField.getInt(checker);
        // anyPolicyInhibited true => inhibitAnyPolicy = 0
        if (inhibitAnyPolicy != 0) {
            throw new RuntimeException("inhibitAnyPolicy expected 0 but was " + inhibitAnyPolicy);
        }
    }
}
