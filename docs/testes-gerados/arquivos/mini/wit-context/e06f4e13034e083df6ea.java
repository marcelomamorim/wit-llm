/*
 * @test
 * @summary Test CheckSignatureAdapter.visitFormalTypeParameter for correct exception and state handling
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    static final int TYPE_SIGNATURE = CheckSignatureAdapter.TYPE_SIGNATURE;
    static final int EMPTY = 0;
    static final int FORMAL = 1;
    static final int BOUND = 2;

    public static void main(String[] args) {
        testThrowsIllegalStateExceptionOnInvalidState();
        testThrowsIllegalArgumentExceptionOnInvalidName();
        testNormalVisitFormalTypeParameter();
    }

    private static void testThrowsIllegalStateExceptionOnInvalidState() {
        // Create a CheckSignatureAdapter with type != TYPE_SIGNATURE and invalid state
        // We use a dummy SignatureVisitor to avoid NPE
        SignatureVisitor dummy = new SignatureVisitor(0) {};
        CheckSignatureAdapter csa = new CheckSignatureAdapter(0, dummy);

        // Reflectively set state to an invalid value (not EMPTY, FORMAL or BOUND) to simulate invalid state
        // Since state is private, we cannot set it directly, so we simulate by calling visitFormalTypeParameter twice
        // The first call sets state to FORMAL, second call should throw IllegalStateException

        // First call with valid name and initial state EMPTY
        csa.visitFormalTypeParameter("T");

        // Second call should throw IllegalStateException because state is FORMAL now
        try {
            csa.visitFormalTypeParameter("U");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // Expected
        }
    }

    private static void testThrowsIllegalArgumentExceptionOnInvalidName() {
        SignatureVisitor dummy = new SignatureVisitor(0) {};
        CheckSignatureAdapter csa = new CheckSignatureAdapter(0, dummy);

        // The state must be EMPTY initially, so visitFormalTypeParameter can be called
        // But name null or empty string should cause IllegalArgumentException

        // Test null name
        try {
            csa.visitFormalTypeParameter(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null name");
        } catch (IllegalArgumentException iae) {
            // Expected
        }

        // Test empty string name
        try {
            csa.visitFormalTypeParameter("");
            throw new RuntimeException("Expected IllegalArgumentException not thrown for empty name");
        } catch (IllegalArgumentException iae) {
            // Expected
        }
    }

    private static void testNormalVisitFormalTypeParameter() {
        // Test normal usage: valid name and valid state
        final String validName = "T";

        // Create a dummy SignatureVisitor that records if visitFormalTypeParameter is called
        class DummySignatureVisitor extends SignatureVisitor {
            boolean called = false;
            String param = null;

            DummySignatureVisitor() {
                super(0);
            }

            @Override
            public void visitFormalTypeParameter(String name) {
                called = true;
                param = name;
            }
        }

        DummySignatureVisitor dummy = new DummySignatureVisitor();
        CheckSignatureAdapter csa = new CheckSignatureAdapter(0, dummy);

        csa.visitFormalTypeParameter(validName);

        if (!dummy.called) {
            throw new RuntimeException("Expected visitFormalTypeParameter to be called on delegate");
        }
        if (!validName.equals(dummy.param)) {
            throw new RuntimeException("Expected parameter name to be passed correctly");
        }
    }
}
