/*
 * @test
 * @summary Test CheckSignatureAdapter.visitTypeArgument for correct exception and behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitTypeArgumentThrowsOnInvalidState();
        testVisitTypeArgumentThrowsOnInvalidWildcard();
        testVisitTypeArgumentReturnsCheckSignatureAdapter();
    }

    private static void testVisitTypeArgumentThrowsOnInvalidState() {
        // Create a CheckSignatureAdapter with a state different from CLASS_TYPE (128)
        // Using constructor with type = METHOD_SIGNATURE (1) to differ from CLASS_TYPE
        CheckSignatureAdapter csa = new CheckSignatureAdapter(1, (SignatureVisitor) null);
        try {
            csa.visitTypeArgument('+');
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testVisitTypeArgumentThrowsOnInvalidWildcard() {
        // Create a CheckSignatureAdapter with state = CLASS_TYPE (128) to pass first check
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, (SignatureVisitor) null);
        try {
            csa.visitTypeArgument('x'); // 'x' not in "+-="
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testVisitTypeArgumentReturnsCheckSignatureAdapter() {
        // Create a CheckSignatureAdapter with state = CLASS_TYPE (128) and non-null SignatureVisitor
        SignatureVisitor dummyVisitor = new SignatureVisitor(0) {};
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, dummyVisitor);
        SignatureVisitor result = csa.visitTypeArgument('+');
        if (result == null) {
            throw new AssertionError("visitTypeArgument returned null");
        }
        if (!(result instanceof CheckSignatureAdapter)) {
            throw new AssertionError("visitTypeArgument did not return a CheckSignatureAdapter instance");
        }
    }
}
