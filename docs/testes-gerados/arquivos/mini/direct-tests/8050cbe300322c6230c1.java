/*
 * @test
 * @summary Test CheckSignatureAdapter.visitTypeArgument() behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitTypeArgumentThrowsWhenStateNotClassType();
        testVisitTypeArgumentDelegatesToSv();
        testVisitTypeArgumentNoSv();
    }

    private static void testVisitTypeArgumentThrowsWhenStateNotClassType() {
        // Create subclass to set state to invalid value
        CheckSignatureAdapter csa = new CheckSignatureAdapter(0, null) {
            {
                // forcibly set state to invalid value
                try {
                    java.lang.reflect.Field f = CheckSignatureAdapter.class.getDeclaredField("state");
                    f.setAccessible(true);
                    f.setInt(this, 999); // invalid state
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        try {
            csa.visitTypeArgument();
            throw new AssertionError("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testVisitTypeArgumentDelegatesToSv() {
        final boolean[] called = new boolean[1];
        SignatureVisitor sv = new SignatureVisitor(0) {
            @Override
            public void visitTypeArgument() {
                called[0] = true;
            }
        };
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, sv);
        csa.visitTypeArgument();
        if (!called[0]) {
            throw new AssertionError("visitTypeArgument was not delegated to sv");
        }
    }

    private static void testVisitTypeArgumentNoSv() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        // Should not throw
        csa.visitTypeArgument();
    }
}
