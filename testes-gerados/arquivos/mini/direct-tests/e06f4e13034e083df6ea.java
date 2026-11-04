/*
 * @test
 * @summary Test visitFormalTypeParameter method of CheckSignatureAdapter
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testValidStateAndName();
        testInvalidStateThrows();
        testNullSignatureVisitorDelegation();
    }

    private static void testValidStateAndName() {
        // Create a CheckSignatureAdapter with TYPE_SIGNATURE type to allow visitFormalTypeParameter
        SignatureVisitor dummy = new SignatureVisitor(0) {};
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, dummy);

        // Initially state is EMPTY, so visitFormalTypeParameter should throw
        // But for TYPE_SIGNATURE, visitFormalTypeParameter is not allowed, so expect exception
        try {
            csa.visitFormalTypeParameter("T");
            throw new RuntimeException("Expected IllegalStateException for TYPE_SIGNATURE");
        } catch (IllegalStateException ise) {
            // expected
        }

        // Create with METHOD_SIGNATURE type, which allows visitFormalTypeParameter in EMPTY state
        csa = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, dummy);

        // visitFormalTypeParameter with valid identifier
        csa.visitFormalTypeParameter("T");

        // The state should now be FORMAL (1)
        // We cannot access state directly, but calling visitFormalTypeParameter again should be allowed
        // because state == FORMAL
        csa.visitFormalTypeParameter("U");

        // Test that delegation to sv.visitFormalTypeParameter was called without exception
    }

    private static void testInvalidStateThrows() {
        // Create a CheckSignatureAdapter with METHOD_SIGNATURE type
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);

        // Manually simulate invalid state by calling visitClassType to change state
        // We cannot access state directly, so we call visitClassType which sets state to BOUND (2)
        csa.visitClassType("Ljava/lang/String;");

        try {
            csa.visitFormalTypeParameter("T");
            throw new RuntimeException("Expected IllegalStateException due to invalid state");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testNullSignatureVisitorDelegation() {
        // Create CheckSignatureAdapter with METHOD_SIGNATURE and null SignatureVisitor
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, null);

        // visitFormalTypeParameter should not throw if state is EMPTY
        csa.visitFormalTypeParameter("T");

        // No exception means delegation to null sv is handled gracefully
    }
}
