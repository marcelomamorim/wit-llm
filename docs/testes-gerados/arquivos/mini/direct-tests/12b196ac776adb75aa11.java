/*
 * @test
 * @summary Test CheckSignatureAdapter.visitTypeVariable behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitTypeVariableValid();
        testVisitTypeVariableInvalidState();
    }

    private static void testVisitTypeVariableValid() {
        // Create a CheckSignatureAdapter with type TYPE_SIGNATURE (2) and null SignatureVisitor
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        // The initial state is EMPTY, so visitTypeVariable should succeed
        csa.visitTypeVariable("T");
        // After visitTypeVariable, state should be SIMPLE_TYPE, so calling visitTypeVariable again should fail
        boolean exceptionThrown = false;
        try {
            csa.visitTypeVariable("U");
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException on second visitTypeVariable call");
        }
    }

    private static void testVisitTypeVariableInvalidState() {
        // Create a CheckSignatureAdapter with type CLASS_SIGNATURE (0) and null SignatureVisitor
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        boolean exceptionThrown = false;
        try {
            csa.visitTypeVariable("T");
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when type != TYPE_SIGNATURE");
        }
    }
}
