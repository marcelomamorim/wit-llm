/*
 * @test
 * @summary Test CheckSignatureAdapter.visitInterfaceBound() behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitInterfaceBoundValidStates();
        testVisitInterfaceBoundInvalidState();
    }

    private static void testVisitInterfaceBoundValidStates() {
        // Create a CheckSignatureAdapter with state FORMAL and sv null
        CheckSignatureAdapter csaFormal = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null) {
            { state = FORMAL; }
        };
        SignatureVisitor sv1 = csaFormal.visitInterfaceBound();
        if (sv1 == null) {
            throw new AssertionError("visitInterfaceBound() should not return null when sv is null");
        }

        // Create a CheckSignatureAdapter with state BOUND and sv null
        CheckSignatureAdapter csaBound = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null) {
            { state = BOUND; }
        };
        SignatureVisitor sv2 = csaBound.visitInterfaceBound();
        if (sv2 == null) {
            throw new AssertionError("visitInterfaceBound() should not return null when sv is null");
        }

        // Create a CheckSignatureAdapter with state FORMAL and sv non-null
        CheckSignatureAdapter csaFormalNonNull = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, new DummySignatureVisitor()) {
            { state = FORMAL; }
        };
        SignatureVisitor sv3 = csaFormalNonNull.visitInterfaceBound();
        if (sv3 == null) {
            throw new AssertionError("visitInterfaceBound() should not return null when sv is non-null");
        }

        // Create a CheckSignatureAdapter with state BOUND and sv non-null
        CheckSignatureAdapter csaBoundNonNull = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, new DummySignatureVisitor()) {
            { state = BOUND; }
        };
        SignatureVisitor sv4 = csaBoundNonNull.visitInterfaceBound();
        if (sv4 == null) {
            throw new AssertionError("visitInterfaceBound() should not return null when sv is non-null");
        }
    }

    private static void testVisitInterfaceBoundInvalidState() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null) {
            { state = 999; }
        };
        try {
            csa.visitInterfaceBound();
            throw new AssertionError("Expected IllegalArgumentException for invalid state");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    // Dummy SignatureVisitor implementation for testing
    private static class DummySignatureVisitor extends SignatureVisitor {
        public DummySignatureVisitor() {
            super(Opcodes.ASM7);
        }
    }

    // Access to protected state field via subclassing
    private static final int FORMAL = 1;
    private static final int BOUND = 2;
}
