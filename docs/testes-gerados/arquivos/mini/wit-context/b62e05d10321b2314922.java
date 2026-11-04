/*
 * @test
 * @summary Test CheckSignatureAdapter.visitEnd() behavior and exception throwing
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndThrowsIfStateNotClassType();
        testVisitEndSucceedsIfStateIsClassType();
    }

    private static void testVisitEndThrowsIfStateNotClassType() {
        // Create a CheckSignatureAdapter with dummy SignatureVisitor and type METHOD_SIGNATURE (1) to differ from CLASS_SIGNATURE (0)
        SignatureVisitor dummy = new SignatureVisitor(0) {};
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.METHOD_SIGNATURE, dummy);

        // The initial state is not CLASS_SIGNATURE (0), so visitEnd() should throw IllegalStateException
        try {
            csa.visitEnd();
            throw new AssertionError("Expected IllegalStateException not thrown when state != CLASS_SIGNATURE");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    private static void testVisitEndSucceedsIfStateIsClassType() {
        // Create a CheckSignatureAdapter with type CLASS_SIGNATURE (0) so initial state is CLASS_TYPE
        SignatureVisitor dummy = new SignatureVisitor(0) {
            boolean visitEndCalled = false;
            @Override
            public void visitEnd() {
                visitEndCalled = true;
            }
        };

        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, dummy);

        // visitEnd() should not throw and should call sv.visitEnd()
        csa.visitEnd();

        // We cannot access private state, but we can check that sv.visitEnd() was called by overriding
        // However, since visitEndCalled is not accessible, we rely on no exception thrown as success
    }
}
